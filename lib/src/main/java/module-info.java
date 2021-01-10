module lib {
    requires transitive java.rmi;
    exports lib.service;
    exports lib.dto;
    exports lib.exception;
}