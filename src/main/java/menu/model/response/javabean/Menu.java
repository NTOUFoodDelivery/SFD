package menu.model.response.javabean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Menu {
    /**
     * Rest_Id : 12
     * Food_Id : 45
     * Food_Name : kvjfndb
     * Cost : 124
     * Description : ergftegwhj
     * Image : vtyfudj
     */

    @SerializedName("Rest_Id")
    @Expose
    private long restID;
    @SerializedName("Food_Id")
    @Expose
    private long foodID;
    @SerializedName("Food_Name")
    @Expose
    private String Food_Name;
    @SerializedName("Cost")
    @Expose
    private int cost;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("Image")
    @Expose
    private String image;


    public long getRestID() {
        return restID;
    }

    public void setRestID(long restID) {
        this.restID = restID;
    }

    public long getFoodID() {
        return foodID;
    }

    public void setFoodID(long foodID) {
        this.foodID = foodID;
    }

    public String getFood_Name() {
        return Food_Name;
    }

    public void setFood_Name(String food_Name) {
        Food_Name = food_Name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("restID", restID)
                .append("foodID", foodID)
                .append("Food_Name", Food_Name)
                .append("cost", cost)
                .append("description", description)
                .append("image", image)
                .toString();
    }
}
