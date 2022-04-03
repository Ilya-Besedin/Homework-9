package guru.homework.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Item {
    public int id;
    public String name;
    @JsonProperty("russian_name")
    public String russianName;
    public String manufacturer;
    public String image;
    public String price;
    public Boolean available;
}
