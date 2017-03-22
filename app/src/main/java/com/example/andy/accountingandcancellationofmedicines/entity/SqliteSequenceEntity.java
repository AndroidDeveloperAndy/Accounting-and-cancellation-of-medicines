package com.example.andy.accountingandcancellationofmedicines.entity;

public class SqliteSequenceEntity {
    private String name;
    private String seq;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SqliteSequenceEntity that = (SqliteSequenceEntity) o;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (seq != null ? !seq.equals(that.seq) : that.seq != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (seq != null ? seq.hashCode() : 0);
        return result;
    }
}
