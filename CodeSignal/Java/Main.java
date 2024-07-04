import java.util.ArrayList;
import java.util.Arrays;

public class Main
{

    public static void main(String[] args)
    {
        ArrayList<ArrayList<String>> queries = new ArrayList<>();
        queries.add(new ArrayList<>(Arrays.asList("CREATE_ACCOUNT", "1", "acc0")));
        queries.add(new ArrayList<>(Arrays.asList("CREATE_ACCOUNT", "2", "acc1")));
        queries.add(new ArrayList<>(Arrays.asList("CREATE_ACCOUNT", "3", "acc2")));
        queries.add(new ArrayList<>(Arrays.asList("CREATE_ACCOUNT", "4", "acc3")));
        queries.add(new ArrayList<>(Arrays.asList("CREATE_ACCOUNT", "5", "acc4")));
        queries.add(new ArrayList<>(Arrays.asList("CREATE_ACCOUNT", "6", "acc5")));
        queries.add(new ArrayList<>(Arrays.asList("CREATE_ACCOUNT", "7", "acc6")));
        queries.add(new ArrayList<>(Arrays.asList("CREATE_ACCOUNT", "8", "acc7")));
        queries.add(new ArrayList<>(Arrays.asList("CREATE_ACCOUNT", "9", "acc8")));
        queries.add(new ArrayList<>(Arrays.asList("CREATE_ACCOUNT", "10", "acc9")));
        queries.add(new ArrayList<>(Arrays.asList("DEPOSIT", "11", "acc0", "7757")));
        queries.add(new ArrayList<>(Arrays.asList("DEPOSIT", "12", "acc1", "8114")));
        queries.add(new ArrayList<>(Arrays.asList("DEPOSIT", "13", "acc2", "6692")));
        queries.add(new ArrayList<>(Arrays.asList("DEPOSIT", "14", "acc3", "5429")));
        queries.add(new ArrayList<>(Arrays.asList("DEPOSIT", "15", "acc4", "7852")));
        queries.add(new ArrayList<>(Arrays.asList("DEPOSIT", "16", "acc5", "6105")));
        queries.add(new ArrayList<>(Arrays.asList("DEPOSIT", "17", "acc6", "5747")));
        queries.add(new ArrayList<>(Arrays.asList("DEPOSIT", "18", "acc7", "8009")));
        queries.add(new ArrayList<>(Arrays.asList("DEPOSIT", "19", "acc8", "5165")));
        queries.add(new ArrayList<>(Arrays.asList("DEPOSIT", "20", "acc9", "5966")));
        queries.add(new ArrayList<>(Arrays.asList("PAY", "21", "acc0", "344")));
        queries.add(new ArrayList<>(Arrays.asList("PAY", "22", "acc1", "222")));
        queries.add(new ArrayList<>(Arrays.asList("PAY", "23", "acc2", "377")));
        queries.add(new ArrayList<>(Arrays.asList("PAY", "24", "acc3", "172")));
        queries.add(new ArrayList<>(Arrays.asList("PAY", "25", "acc4", "251")));
        queries.add(new ArrayList<>(Arrays.asList("PAY", "26", "acc5", "497")));
        queries.add(new ArrayList<>(Arrays.asList("PAY", "27", "acc6", "472")));
        queries.add(new ArrayList<>(Arrays.asList("PAY", "28", "acc7", "103")));
        queries.add(new ArrayList<>(Arrays.asList("PAY", "29", "acc8", "171")));
        queries.add(new ArrayList<>(Arrays.asList("PAY", "30", "acc9", "448")));
        queries.add(new ArrayList<>(Arrays.asList("TRANSFER", "31", "acc6", "acc0", "1358")));
        queries.add(new ArrayList<>(Arrays.asList("TRANSFER", "32", "acc0", "acc1", "1150")));
        queries.add(new ArrayList<>(Arrays.asList("TRANSFER", "33", "acc3", "acc2", "1235")));
        queries.add(new ArrayList<>(Arrays.asList("TRANSFER", "34", "acc0", "acc3", "1539")));
        queries.add(new ArrayList<>(Arrays.asList("TRANSFER", "35", "acc2", "acc4", "1253")));
        queries.add(new ArrayList<>(Arrays.asList("TRANSFER", "36", "acc2", "acc5", "1397")));
        queries.add(new ArrayList<>(Arrays.asList("TRANSFER", "37", "acc5", "acc6", "1861")));
        queries.add(new ArrayList<>(Arrays.asList("TRANSFER", "38", "acc2", "acc7", "1518")));
        queries.add(new ArrayList<>(Arrays.asList("TRANSFER", "39", "acc3", "acc8", "1635")));
        queries.add(new ArrayList<>(Arrays.asList("TRANSFER", "40", "acc1", "acc9", "1669")));
        queries.add(new ArrayList<>(Arrays.asList("TOP_ACTIVITY", "41", "10")));
        queries.add(new ArrayList<>(Arrays.asList("DEPOSIT", "86400041", "acc0", "506")));
        queries.add(new ArrayList<>(Arrays.asList("DEPOSIT", "86400042", "acc1", "276")));
        queries.add(new ArrayList<>(Arrays.asList("DEPOSIT", "86400043", "acc2", "361")));
        queries.add(new ArrayList<>(Arrays.asList("DEPOSIT", "86400044", "acc3", "757")));
        queries.add(new ArrayList<>(Arrays.asList("DEPOSIT", "86400045", "acc4", "129")));
        queries.add(new ArrayList<>(Arrays.asList("DEPOSIT", "86400046", "acc5", "477")));
        queries.add(new ArrayList<>(Arrays.asList("DEPOSIT", "86400047", "acc6", "676")));
        queries.add(new ArrayList<>(Arrays.asList("DEPOSIT", "86400048", "acc7", "754")));
        queries.add(new ArrayList<>(Arrays.asList("DEPOSIT", "86400049", "acc8", "873")));
        queries.add(new ArrayList<>(Arrays.asList("DEPOSIT", "86400050", "acc9", "242")));
        queries.add(new ArrayList<>(Arrays.asList("ACCEPT_TRANSFER", "86400051", "acc0", "transfer1")));
        queries.add(new ArrayList<>(Arrays.asList("ACCEPT_TRANSFER", "86400052", "acc1", "transfer2")));
        queries.add(new ArrayList<>(Arrays.asList("ACCEPT_TRANSFER", "86400053", "acc2", "transfer3")));
        queries.add(new ArrayList<>(Arrays.asList("ACCEPT_TRANSFER", "86400054", "acc3", "transfer4")));
        queries.add(new ArrayList<>(Arrays.asList("ACCEPT_TRANSFER", "86400055", "acc4", "transfer5")));
        queries.add(new ArrayList<>(Arrays.asList("ACCEPT_TRANSFER", "86400056", "acc5", "transfer6")));
        queries.add(new ArrayList<>(Arrays.asList("ACCEPT_TRANSFER", "86400057", "acc6", "transfer7")));
        queries.add(new ArrayList<>(Arrays.asList("ACCEPT_TRANSFER", "86400058", "acc7", "transfer8")));
        queries.add(new ArrayList<>(Arrays.asList("ACCEPT_TRANSFER", "86400059", "acc8", "transfer9")));
        queries.add(new ArrayList<>(Arrays.asList("ACCEPT_TRANSFER", "86400060", "acc9", "transfer10")));
        queries.add(new ArrayList<>(Arrays.asList("TOP_ACTIVITY", "86400061", "10")));
        queries.add(new ArrayList<>(Arrays.asList("PAY", "86400062", "acc0", "348")));
        queries.add(new ArrayList<>(Arrays.asList("PAY", "86400063", "acc1", "166")));
        queries.add(new ArrayList<>(Arrays.asList("PAY", "86400064", "acc2", "281")));
        queries.add(new ArrayList<>(Arrays.asList("PAY", "86400065", "acc3", "267")));
        queries.add(new ArrayList<>(Arrays.asList("PAY", "86400066", "acc4", "421")));
        queries.add(new ArrayList<>(Arrays.asList("PAY", "86400067", "acc5", "387")));
        queries.add(new ArrayList<>(Arrays.asList("PAY", "86400068", "acc6", "172")));
        queries.add(new ArrayList<>(Arrays.asList("PAY", "86400069", "acc7", "427")));
        queries.add(new ArrayList<>(Arrays.asList("PAY", "86400070", "acc8", "481")));
        queries.add(new ArrayList<>(Arrays.asList("PAY", "86400071", "acc9", "459")));
        queries.add(new ArrayList<>(Arrays.asList("TRANSFER", "86400073", "acc9", "acc0", "1938")));
        queries.add(new ArrayList<>(Arrays.asList("TRANSFER", "86400074", "acc3", "acc1", "1764")));
        queries.add(new ArrayList<>(Arrays.asList("TRANSFER", "86400075", "acc7", "acc2", "1660")));
        queries.add(new ArrayList<>(Arrays.asList("TRANSFER", "86400076", "acc4", "acc3", "1541")));
        queries.add(new ArrayList<>(Arrays.asList("TRANSFER", "86400077", "acc8", "acc4", "1379")));
        queries.add(new ArrayList<>(Arrays.asList("TRANSFER", "86400078", "acc2", "acc5", "1219")));
        queries.add(new ArrayList<>(Arrays.asList("TRANSFER", "86400079", "acc5", "acc6", "1519")));
        queries.add(new ArrayList<>(Arrays.asList("TRANSFER", "86400080", "acc2", "acc7", "1832")));
        queries.add(new ArrayList<>(Arrays.asList("TRANSFER", "86400081", "acc6", "acc8", "1645")));
        queries.add(new ArrayList<>(Arrays.asList("TRANSFER", "86400082", "acc2", "acc9", "1960")));
        queries.add(new ArrayList<>(Arrays.asList("ACCEPT_TRANSFER", "172800083", "acc0", "transfer11")));
        queries.add(new ArrayList<>(Arrays.asList("ACCEPT_TRANSFER", "172800084", "acc1", "transfer12")));
        queries.add(new ArrayList<>(Arrays.asList("ACCEPT_TRANSFER", "172800085", "acc2", "transfer13")));
        queries.add(new ArrayList<>(Arrays.asList("ACCEPT_TRANSFER", "172800086", "acc3", "transfer14")));
        queries.add(new ArrayList<>(Arrays.asList("ACCEPT_TRANSFER", "172800087", "acc4", "transfer15")));
        queries.add(new ArrayList<>(Arrays.asList("ACCEPT_TRANSFER", "172800088", "acc5", "transfer16")));
        queries.add(new ArrayList<>(Arrays.asList("ACCEPT_TRANSFER", "172800089", "acc6", "transfer17")));
        queries.add(new ArrayList<>(Arrays.asList("ACCEPT_TRANSFER", "172800090", "acc7", "transfer18")));
        queries.add(new ArrayList<>(Arrays.asList("ACCEPT_TRANSFER", "172800091", "acc8", "transfer19")));
        queries.add(new ArrayList<>(Arrays.asList("ACCEPT_TRANSFER", "172800092", "acc9", "transfer20")));
        queries.add(new ArrayList<>(Arrays.asList("TOP_ACTIVITY", "172800094", "10")));

        ArrayList<String> result = new ArrayList<>();
        result.add("true");
        result.add("true");
        result.add("true");
        result.add("true");
        result.add("true");
        result.add("true");
        result.add("true");
        result.add("true");
        result.add("true");
        result.add("true");
        result.add("7757");
        result.add("8114");
        result.add("6692");
        result.add("5429");
        result.add("7852");
        result.add("6105");
        result.add("5747");
        result.add("8009");
        result.add("5165");
        result.add("5966");
        result.add("7413");
        result.add("7892");
        result.add("6315");
        result.add("5257");
        result.add("7601");
        result.add("5608");
        result.add("5275");
        result.add("7906");
        result.add("4994");
        result.add("5518");
        result.add("transfer1");
        result.add("transfer2");
        result.add("transfer3");
        result.add("transfer4");
        result.add("transfer5");
        result.add("transfer6");
        result.add("transfer7");
        result.add("transfer8");
        result.add("transfer9");
        result.add("transfer10");
        result.add("acc1(8336), acc7(8112), acc4(8103), acc0(8101), acc2(7069), acc5(6602), acc9(6414), acc6(6219), acc3(5601), acc8(5336)");
        result.add("7919");
        result.add("8168");
        result.add("6676");
        result.add("6014");
        result.add("7730");
        result.add("6085");
        result.add("5951");
        result.add("8660");
        result.add("5867");
        result.add("5760");
        result.add("false");
        result.add("false");
        result.add("false");
        result.add("false");
        result.add("false");
        result.add("false");
        result.add("false");
        result.add("false");
        result.add("false");
        result.add("false");
        result.add("acc7(8866), acc1(8612), acc0(8607), acc4(8232), acc2(7430), acc5(7079), acc6(6895), acc9(6656), acc3(6358), acc8(6209)");
        result.add("7571");
        result.add("8002");
        result.add("6395");
        result.add("5747");
        result.add("7309");
        result.add("5698");
        result.add("5779");
        result.add("8233");
        result.add("5386");
        result.add("5301");
        result.add("transfer11");
        result.add("transfer12");
        result.add("transfer13");
        result.add("transfer14");
        result.add("transfer15");
        result.add("transfer16");
        result.add("transfer17");
        result.add("transfer18");
        result.add("transfer19");
        result.add("transfer20");
        result.add("false");
        result.add("false");
        result.add("false");
        result.add("false");
        result.add("false");
        result.add("false");
        result.add("false");
        result.add("false");
        result.add("false");
        result.add("false");
        result.add("acc7(9293), acc0(8955), acc1(8778), acc4(8653), acc2(7711), acc5(7466), acc9(7115), acc6(7067), acc8(6690), acc3(6625)");

        ArrayList<String> user = Bank.Solution(queries);
        int l = user.size();
        for(int i=0; i< l; i++)
            if (!user.get(i).equals(result.get(i)))
                System.out.printf("%1$n: %2$s [ %3$s ]%n", i, user.get(i), result.get(i));
    }
}
