package sg.edu.nus.iss.app;

import java.io.Console;
import java.util.LinkedList;
import java.util.List;

public class Session {

    private static final String LIST="list";
    private static final String ADD="add";
    private static final String DELETE="del";
    private static final String USERS="users";
    private static final String SAVE="save";
    private static final String END="end";
    private static final String LOGIN="login";

    private Repository repository;
    private ShoppingCart currCart;
    private List<String> tempItemsList = new LinkedList<>();

    public Session(Repository repo){
        this.repository = repo;
    }

    public void start(){
        Console cons = System.console();
        boolean stop =false;
        currCart = new ShoppingCart("anon");
        while(!stop){
            String input = cons.readLine("> ");
            String[] terms = input.split(" ");

            switch(terms[0]){
                case LIST:
                    System.out.printf("Items in %s's "
                         + "shopping cart\n", currCart.getUsername());
                    if(tempItemsList.size() > 0){
                        System.out.println("Please save before list");
                    }else{
                        currCart = repository.load(currCart.getUsername());
                        printList(currCart.getContents());
                    }
                    break;
                case ADD:
                    int before = currCart.getContents().size();
                    for(int x =1; x < terms.length; x++){
                        tempItemsList.add(terms[x]);
                        currCart.add(terms[x]);
                    }
                        
                    int addedCount = currCart.getContents().size() - before;
                    System.out.printf("Added %d item(s) to %s's cart\n"
                        ,addedCount, currCart.getUsername());
                    break;
                case DELETE:
                    System.out.println(terms[1]);
                    int idx  = Integer.parseInt(terms[1]);
                    String item = currCart.delete(idx - 1);
                    repository.save(currCart);
                    System.out.printf("Removed %s from %s's cart",
                        item, currCart.getUsername());
                    break;
                case SAVE:
                    repository.save(currCart);
                    tempItemsList.clear();
                    break;
                case LOGIN:
                    currCart = new ShoppingCart(terms[1]);
                    break;
                case END:
                    stop = true;
                    break;
                case USERS:
                    List<String> allCarts = repository.getShoppingCartsDbFiles();
                    this.printList(allCarts);
                    break;
                default: 
                    System.err.printf("Unknown input: %s\n", terms[0]);
            }
            System.out.println("");
        }
        System.out.println("Thank you !");
    }

    public void printList(List<String> list){
        if(list.size() <=0 ){
            System.out.println("No record found!");
            return;
        }

        for(int i=0; i < list.size(); i++){
            System.out.printf("%d %s\n", (i+1), list.get(i));
        }
    }

}