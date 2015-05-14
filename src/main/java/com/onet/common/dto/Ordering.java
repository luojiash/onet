package com.onet.common.dto;

import java.io.Serializable;

import com.onet.common.constant.Sequence;

public class Ordering implements Serializable{
    private static final long serialVersionUID = 727555874282630721L;
    private String field;
    private Sequence sequence;

    public Ordering() {
	}
	public Ordering(String field, Sequence sequence) {
		this.field = field;
		this.sequence = sequence;
	}
	public String getField() {
        return field;
    }
    public void setField(String field) {
        this.field = field;
    }
	public Sequence getSequence() {
		return sequence;
	}
	public void setSequence(Sequence sequence) {
		this.sequence = sequence;
	}
}
