import java.util.ArrayList;

public class Data
{
    ArrayList<Seller> sellerList;
    ArrayList<Client> clientList;
    ArrayList<Item> itemList;
    ArrayList<Basket> basketList;

    public Data(ArrayList<Seller> sellerList, ArrayList<Client> clientList, ArrayList<Item> itemList, ArrayList<Basket> basketList)
    {
        this.sellerList = sellerList;
        this.clientList = clientList;
        this.itemList = itemList;
        this.basketList = basketList;
    }

    public void load()
    {

    }

    public void save()
    {

    }

    public int calculateAllBasketsForClient(int id)
    {
        int tempTotal = 0;
        for(Basket b : basketList)
        {
            if(b.getClientId() == id)
            {
                for (Item i : b.items)
                {
                    tempTotal = tempTotal + i.getPrice() * i.getAmount();
                }
            }
        }
        return tempTotal;
    }

    public int calculateAllBasketsForSeller(int id)
    {
        int tempTotal = 0;
        for(Basket b : basketList)
        {
            if(b.getSellerId() == id)
            {
                for (Item i : b.items)
                {
                    tempTotal = tempTotal + i.getPrice() * i.getAmount();
                }
            }
        }
        return tempTotal;
    }

}
