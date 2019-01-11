package ir.game.models.beans;

import java.util.List;

public class ResponseList<T> extends ResponseBean {

    private List<T> result;

    public ResponseList(Integer statusCode, String statusDesc,List<T> result) {
        super(statusCode, statusDesc);
        this.result=result;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }
}
