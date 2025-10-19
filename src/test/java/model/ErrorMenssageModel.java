package model;

import com.google.gson.annotations.Expose;
import lombok.Data;

@Data
public class ErrorMenssageModel {
    @Expose
    private String message;
}
