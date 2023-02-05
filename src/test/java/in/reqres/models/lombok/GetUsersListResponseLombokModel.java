package in.reqres.models.lombok;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetUsersListResponseLombokModel {

    private int page;
    @JsonProperty("per_page")
    private int perPage;
    private int total;
    @JsonProperty("total_pages")
    private int totalPages;
    private ArrayList<UserData> data;
    private Support support;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Support{
        private String url;
        private String text;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class UserData{
        private int id;
        private String name;
        private String color;
        @JsonProperty("pantone_value")
        private String pantoneValue;
    }

}







