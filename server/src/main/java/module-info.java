module server {
    requires java.persistence;
    requires lib;
    opens server.model to org.hibernate.orm.core;

    requires java.sql.rowset;
    requires net.bytebuddy;
    requires com.fasterxml.classmate;
    requires java.xml.bind;

}