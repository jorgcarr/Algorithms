import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.stream.IntStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.*;


public class Crawler {

    public static void main(String[] args) throws Exception {


        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://en.wikipedia.org/wiki/Special:Random"))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        String body = response.body();
        Document doc = buildDocument(body);
        printTitle(doc);
        printCategories(doc);
    }

    public static Document buildDocument(String document) throws Exception {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new ByteArrayInputStream(
                document.getBytes(StandardCharsets.UTF_8)));
        return doc;
    }

    private static void printCategories(Document doc) throws Exception {
        String expression = "//body/" +
                "div[@id ='content']/" +
                "div[@id ='bodyContent']/" +
                "div[@id ='catlinks']/" +
                "div[@id ='mw-normal-catlinks']/" +
                "ul";

        NodeList nodes = doQuery(doc, expression);

        IntStream.range(0, nodes.getLength()).
                forEach(
                        i ->System.out.println(
                                "Categories: " + nodes.item(i).getTextContent()));

    }

    private static void printTitle(Document doc) throws Exception {

        String expression = "//body/" +
                "div[@id ='content']/" +
                "h1[@id ='firstHeading']";

        NodeList nodes = doQuery(doc, expression);

        Node firstChildNode = nodes.item(0).
                getFirstChild();

        if (firstChildNode.getNodeValue() == null) {
            printTitle(firstChildNode.getFirstChild());
        } else {
            printTitle(firstChildNode);
        }


    }

    static void printTitle(Node node) {
        System.out.println("title: " + node.getNodeValue());
    }

    private static NodeList doQuery(Document doc, String expression) throws Exception {

        XPath xPath = XPathFactory.newInstance().newXPath();
        XPathExpression expr = xPath.compile(expression);
        Object result = expr.evaluate(doc, XPathConstants.NODESET);

        return (NodeList) result;
    }
}
