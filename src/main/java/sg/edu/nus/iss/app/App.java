package sg.edu.nus.iss.app;

public class App 
{
    private static String defaultDb = "db";
    public static void main( String[] args )
    {
        if(args.length > 0){
            App.defaultDb = args[0];
            System.out.println(App.defaultDb);

        }else{
            //to assign String userDir of my current working directory. i.e C:\opt\tfip\workshop3>
            //standard code >>> System.getProperty("user.dir")
            String userDir = System.getProperty("user.dir");

            System.out.println(userDir);

            // !!!!!!!!!!!!! confirm whether to use \ or /
            App.defaultDb = userDir + "/" + defaultDb;
            System.out.println(App.defaultDb);
        }
        // init Repository passing the working folder path address, String defaultDB
        Repository repo = new Repository(defaultDb);

        Session sess = new Session(repo);
        sess.start();
    }
}