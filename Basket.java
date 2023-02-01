import java.time.LocalDateTime;
import java.util.ArrayList;

public class Basket
{
    int clientId;
    int sellerId;
    LocalDateTime time;
    int totalPrice;
    ArrayList<Item> items;

    public Basket(int clientId, int sellerId, LocalDateTime time, ArrayList<Item> items)
    {
        this.clientId = clientId;
        this.sellerId = sellerId;
        this.time = time;
        this.items = items;
        calculateTotalPrice();
    }

    public void calculateTotalPrice()
    {
        int tempTotalPrice = 0;
        for(Item i : items)
        {
            tempTotalPrice = tempTotalPrice + i.getPrice() * i.getAmount();
        }
        totalPrice = tempTotalPrice;
    }

    public void addItem(Item i)
    {
        items.add(i);
        calculateTotalPrice();
    }

    public void removeItem(Item i)
    {
        items.removeIf(j -> j.name.equals(i.getName()));
    }



    public int getClientId()
    {
        return clientId;
    }

    public void setClientId(int clientId)
    {
        this.clientId = clientId;
    }

    public int getSellerId()
    {
        return sellerId;
    }

    public void setSellerId(int sellerId)
    {
        this.sellerId = sellerId;
    }

    public LocalDateTime getTime()
    {
        return time;
    }

    public void setTime(LocalDateTime time)
    {
        this.time = time;
    }

    public int getTotalPrice()
    {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice)
    {
        this.totalPrice = totalPrice;
    }

    public ArrayList<Item> getItems()
    {
        return items;
    }

    public void setItems(ArrayList<Item> items)
    {
        this.items = items;
    }
}
