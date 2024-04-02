package codex.lb04.Utils;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * DOES NOTHING AT THE MOMENT
 */
public class PDFExtractor {
    /**
     * generates a deck (only images)
     * @param args
     */
    public static void main(String[] args) {
        File pdf = new File("src/main/resources/graphics/CODEX_cards_gold_back.pdf");
        try {
            PDDocument doc = PDFParser.load(pdf);
            List<RenderedImage> list = getImagesFromPDF(doc);
            for (RenderedImage image: list){
                System.out.println(image.getData().toString());
            }
            doc.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<RenderedImage> getImagesFromPDF(PDDocument doc) {
        List<RenderedImage> images = new ArrayList<>();
        for (PDPage page : doc.getPages()) {
            images.addAll(getImagesFromResources(page.getResources()));
        }
        return images;
    }

    private static List<RenderedImage> getImagesFromResources(PDResources resources) {
        List<RenderedImage> images = new ArrayList<>();
        for (COSName xObjectName: resources.getXObjectNames()){
            PDXObject xObject = null;
            try {
                xObject = resources.getXObject(xObjectName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (xObject instanceof PDFormXObject){
                images.addAll(getImagesFromResources(((PDFormXObject)xObject).getResources()));
            } else if (xObject instanceof PDImageXObject) {
                try {
                    images.add(((PDImageXObject)xObject).getImage());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }
        return images;
    }

    public static void PDFBoxExtractImages(PDDocument document){
        PDPageTree list = document.getPages();
        for(PDPage page: list){
            PDResources pdResources = page.getResources();
            for (COSName c : pdResources.getXObjectNames()){
                PDXObject o = null;
                try {
                    o = pdResources.getXObject(c);
                    if(o instanceof PDImageXObject) {
                        File file = new File("src/main/resources/graphics/" + System.nanoTime() + ".png");
                        ImageIO.write(((PDImageXObject) o).getImage(), "png", file);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
