package com.rgbrain.brianbot.domain.brian;

public class TestResponse {
    private String data;

    public TestResponse() {
    }

    public TestResponse(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        TestResponse that = (TestResponse) o;
        return java.util.Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(data);
    }
}
