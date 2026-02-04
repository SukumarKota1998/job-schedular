package airtribe.job_schedular.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseBean {

    private String message;

    private Object data;

    public ResponseBean(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public ResponseBean() {
    }

}
