package forum.server.domainlayer;

import org.apache.lucene.store.jdbc.dialect.MySQLInnoDBDialect;

public class MyDriver extends MySQLInnoDBDialect {

    public String openBlobSelectQuote() {
        return "`";
    }

    public String closeBlobSelectQuote() {
        return "`";
    }
}
