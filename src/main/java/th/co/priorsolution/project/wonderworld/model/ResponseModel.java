package th.co.priorsolution.project.wonderworld.model;

import lombok.Data;

@Data
public class ResponseModel<T> {

    private int statusCode;
    private String description;
    private T data;

}
