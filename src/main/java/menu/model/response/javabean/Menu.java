package menu.model.response.javabean;

public class Menu {
    /**
     * Rest_Id : 12
     * Food_Id : 45
     * Food_Name : kvjfndb
     * Cost : 124
     * Description : ergftegwhj
     * Image : vtyfudj
     */

    private long Rest_Id;
    private long Food_Id;
    private String Food_Name;
    private int Cost;
    private String Description;
    private String Image;

    public long getRest_Id() {
        return Rest_Id;
    }

    public void setRest_Id(long Rest_Id) {
        this.Rest_Id = Rest_Id;
    }

    public long getFood_Id() {
        return Food_Id;
    }

    public void setFood_Id(long Food_Id) {
        this.Food_Id = Food_Id;
    }

    public String getFood_Name() {
        return Food_Name;
    }

    public void setFood_Name(String Food_Name) {
        this.Food_Name = Food_Name;
    }

    public int getCost() {
        return Cost;
    }

    public void setCost(int Cost) {
        this.Cost = Cost;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }

}
