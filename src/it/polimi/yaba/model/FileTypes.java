package it.polimi.yaba.model;

public enum FileTypes {

    jpg("jpg"), png("png"), pdf("pdf"), jpeg("jpeg"), gif("gif");

    private String name;

    private FileTypes(String s) {
        name = s;

    }

    @Override
    public String toString() {
        return name;
    }
}
