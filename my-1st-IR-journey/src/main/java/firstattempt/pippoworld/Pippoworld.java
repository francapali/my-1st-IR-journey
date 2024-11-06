
package firstattempt.pippoworld;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

/**
 * This class demonstrates a basic implementation of Apache Lucene to create, index,
 * and search documents. It indexes a set of movie descriptions from "The Hunger Games"
 * franchise and allows basic search functionality on the "Plot" field.
 * <p>
 * Example usage includes:
 * - Opening an index directory
 * - Configuring an IndexWriter and adding documents
 * - Using an IndexSearcher to find matches based on plot keywords
 * </p>
 * 
 * @version 1.0
 * @since 2024-11-06
 */
public class Pippoworld {

    /**
     * Main method that initializes the index directory, creates documents, and
     * performs a search on the indexed content.
     * 
     * @param args Command line arguments (not used in this example).
     * @throws ParseException if the search query cannot be parsed.
     */
    public static void main(String[] args) throws ParseException {
        try {

            /**
             * Opens the directory from the file system for indexing.
             * Configures the IndexWriter to create a new index or overwrite an existing one.
             * 
             * @throws IOException if there is an error opening the directory.
             */
            FSDirectory fsdir = FSDirectory.open(new File("./resources/docs").toPath());
            IndexWriterConfig iwc = new IndexWriterConfig(new StandardAnalyzer());
            iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
            IndexWriter writer = new IndexWriter(fsdir, iwc);

            /**
             * Creates and populates five Document objects with movie details.
             * Each document represents a different movie and contains fields for
             * Title, Director, and Plot.
             */
            Document doc = new Document();
            Document doc1 = new Document();
            Document doc2 = new Document();
            Document doc3 = new Document();
            Document doc4 = new Document();

            // Example movie documents with titles, directors, and plot descriptions
            doc.add(new TextField("Title", "The Hunger Games", Field.Store.YES));         
            doc.add(new TextField("Director", "Gary Ross", Field.Store.NO));
            doc.add(new TextField("Plot", "Katniss volunteers to replace her sister in a tournament that ends only when one participant remains. Pitted against contestants who have trained for this all their life, she has little to rely on.", Field.Store.NO));
            
            doc1.add(new TextField("Title", "The Hunger Games: Catching Fire", Field.Store.YES));
            doc1.add(new TextField("Director", "Francis Lawrence", Field.Store.NO));
            doc1.add(new TextField("Plot", "Katniss is advised by President Snow to participate in a special edition of the Hunger Games that will feature all its previous winners, in order to eliminate her, due to her influential nature.", Field.Store.NO));

            doc2.add(new TextField("Title", "The Hunger Games: Mockingjay Part 1", Field.Store.YES));
            doc2.add(new TextField("Director", "Francis Lawrence", Field.Store.NO));
            doc2.add(new TextField("Plot", "After putting a permanent end to the games, Katniss Everdeen, Gale, Finnick and Beetee join forces to save Peeta and a nation that she has inspired by her courage.", Field.Store.NO));

            doc3.add(new TextField("Title", "The Hunger Games: Mockingjay Part 2", Field.Store.YES));
            doc3.add(new TextField("Director", "Francis Lawrence", Field.Store.NO));
            doc3.add(new TextField("Plot", "After realising that she is no longer fighting for survival, Katniss Everdeen sets out to assassinate the tyrannical President Snow and liberate the people of Panem.", Field.Store.NO));
           
            doc4.add(new TextField("Title", "The Hunger Games: The Ballad of Songbirds and Snakes", Field.Store.YES));
            doc4.add(new TextField("Director", "Francis Lawrence", Field.Store.NO));
            doc4.add(new TextField("Plot","Years before he becomes the tyrannical president of Panem, 18-year-old Coriolanus Snow remains the last hope for his fading lineage. With the 10th annual Hunger Games fast approaching, the young Snow becomes alarmed when he's assigned to mentor Lucy Gray Baird from District 12. Uniting their instincts for showmanship and political savvy, they race against time to ultimately reveal who's a songbird and who's a snake.", Field.Store.NO));
           
           
            // Other document entries...
            writer.addDocument(doc);
            writer.addDocument(doc1);
            writer.addDocument(doc2);
            writer.addDocument(doc3);
            writer.addDocument(doc4);

            writer.close();

            /**
             * Initializes an IndexReader and counts the total number of active documents.
             * Then, an IndexSearcher is created to search the indexed documents.
             * 
             * @throws IOException if there is an error accessing the index.
             */
            IndexReader reader = DirectoryReader.open(fsdir);
            int numDocs = reader.numDocs();
            System.out.println("Docs found: " + numDocs);

            IndexSearcher searcher = new IndexSearcher(DirectoryReader.open(fsdir));

            /**
             * Sets up a QueryParser to search within the "Plot" field.
             * Parses a query to find all documents that contain the term "Peeta"
             * and prints the total occurrences found.
             * 
             * @throws ParseException if the query syntax is invalid.
             */
            QueryParser qp = new QueryParser("Plot", new StandardAnalyzer());
            Query q = qp.parse("Peeta");
            TopDocs topdocs = searcher.search(q, 10);
            System.out.println("Found in " + q.toString() + " " + topdocs.totalHits.value + " time(s).");

        } catch (IOException ex) {
            Logger.getLogger(Pippoworld.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
