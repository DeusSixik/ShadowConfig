package net.shadowking21.shadowconfig.config.model;

public class SCModel<M> {

    public String comment;
    public M value;

    public SCModel(String comment, M value) {
        this.comment = comment;
        this.value = value;
    }
}