package com.zoho.registartionform.models;


import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class Helper {
   private static Persistance db = new QueryHandler();
//    public Helper(){
//        try {
//            FileReader reader = new FileReader("dbConnector.properties");
//            Properties props = new Properties();
//            props.load(reader);
//            String path = (String) props.get("dbName");
//            db = (Persistance) Class.forName(path).newInstance();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
   	public void initialization() throws ExceptionHandling{
   		customerDataStore();
		accountDataStore();
		wholeDataCheck();
   	}
    public HashMap<Integer, CustomerInfo> customerDataStore() throws ExceptionHandling{
        HashMap<Integer, CustomerInfo>customerDataMap;
            customerDataMap=db.customerRetrival();
        MapHandler.OBJECT.customerMapper(customerDataMap);
        return customerDataMap;
    }
    public void accountDataStore() {
        HashMap<Integer,HashMap<Integer, AccountInfo>>accountDataMap=null;
        try {
            accountDataMap=db.accountRetrival();
        }catch(Exception e){
            e.printStackTrace();
        }
        MapHandler.OBJECT.accountMapper(accountDataMap);
    }
    public HashMap<Integer,HashMap<Integer, String>> wholeDataCheck()throws ExceptionHandling{
        HashMap<Integer,HashMap<Integer, String>> wholeMap =db.wholeAccountDetails();
        return wholeMap;
    }
    public boolean wholeAccountCheck(int id,int account_no)throws ExceptionHandling{
        HashMap<Integer,HashMap<Integer, String>>accountMap = wholeDataCheck();
        HashMap<Integer, String> accountNumberMap =accountMap.get(id);
        return accountNumberMap.containsKey(account_no);
    }
    public HashMap<String,String> caseNewUser(ArrayList<Object> inputList,int size) throws ExceptionHandling {
        HashMap<String,String>insertionStatus = new HashMap();
        ArrayList<Integer> idList = db.customerInsertion(inputList);
        if(inputList.size()==idList.size()) {
            dataInsertion(inputList,size,idList,insertionStatus);
        }
        else{
            int index;
            int count=0;
            for(int i=0;i< idList.size();i++){
                if(idList.get(i)<0){
                    if(count==0)
                        index =(i*2);
                    else
                        index=(i*2)-(count*2);
                    CustomerInfo details = (CustomerInfo)inputList.get(index);
                    String item ="Name :"+details.getName()+" customer_id:"+details.getCustomerId();
                    insertionStatus.put(item,"Fail(Customer details)");
                    inputList.remove(index);
                    inputList.remove(index);
                    count++;
                }
            }
            dataInsertion(inputList,size,idList,insertionStatus);
        }
        try{
            db.customerRetrival();
        }catch(Exception e){
            e.printStackTrace();
        }
        return insertionStatus;
    }
    public void dataInsertion(ArrayList<Object> inputList,int size,
                                     ArrayList<Integer> idList,HashMap<String,String>insertionStatus) throws ExceptionHandling {
        int j=0,k=1;
        for (int i =size;i < idList.size(); i++) {
            CustomerInfo customerObject = (CustomerInfo) inputList.get(j);
            customerObject.setCustomerId(idList.get(i));
            j += 2;
            AccountInfo accountObject = (AccountInfo) inputList.get(k);
            accountObject.setCustomer_id(idList.get(i));
            k+=2;
            ArrayList<Integer> account_no = db.accountInsertion(accountObject);
            if(account_no.get(0)>0) {
                accountObject.setAccount_no(account_no.get(1));
                MapHandler.OBJECT.customerMapper(customerObject);
                MapHandler.OBJECT.accountMapper(accountObject);
                String item ="Name :"+customerObject.getName()+" customer_id:"+customerObject.getCustomerId()+" account no:"+accountObject.getAccount_no();
                insertionStatus.put(item, "Success");
            }
            else {
                String item ="Name :"+customerObject.getName()+" customer_id:"+customerObject.getCustomerId();
                insertionStatus.put(item, "Fail(account details)");
                int customer_id = customerObject.getCustomerId();
                db.customerDeletion(customer_id);
            }
        }
    }
    public  String caseExistingUser(AccountInfo in) throws ExceptionHandling {
    	 String idStatus2 = Helper.idCheck(in.getCustomer_id());
         if (idStatus2.equals("valid")) {

        ArrayList<Integer>accountIdList = db.accountInsertion(in);
        if(accountIdList.get(0)>0) {
            in.setAccount_no(accountIdList.get(1));
            MapHandler.OBJECT.accountMapper(in);
        }
        if (accountIdList.get(0) >0)
            return "account insertion successful";
        else
            return "Account insertion Failed";
         }
         else
        	 return "Deactivated Customer..so Can't Add account";
    }
    public  String entireDeletion(AccountInfo info,String type) throws ExceptionHandling {
        int status = db.dataUpdation(info,type);
        MapHandler.OBJECT.customerDeletion(info.getCustomer_id());
        MapHandler.OBJECT.accountDeletion(info.getCustomer_id());
        if (status > 0)
            return "Deleted Successfully";
        else
            return "Deletion Failed";
    }
    public String activateCustomer(int id) throws ExceptionHandling {
        int value=db.customerActivation(id);
        if(value==1){
            return "Customer Activated";
        }
        else
            return "customer Activation Failed";
    }
    public String activateAccount(int account_no) throws ExceptionHandling {
        int value =db.accountActivation(account_no);
        if(value==1)
            return "Account Activation successfully";
        else
            return "Account Activation Failed";
    }
    public static String particularAccountDeletion(AccountInfo info,String type) throws ExceptionHandling {
        HashMap<Integer,HashMap<Integer, AccountInfo>>accountMap =MapHandler.OBJECT.retriveAccountDetails();
        HashMap<Integer, AccountInfo>inner = accountMap.get(info.getCustomer_id());
        int status=0;
        if(inner.size()==1) {
            status = db.dataUpdation(info, "customer");
        }
        else
            status = db.dataUpdation(info, type);
        if(status==1) {
            if (inner != null) {
                inner.remove(info.getAccount_no());
                return "Deletion Successful";
            }
            else
                return "Deletion Failed";
        }
        else
            return "Deletion Failed";
    }
    public void password(CustomerInfo info){
        db.passwordSetter(info);
    }
    public static String amountWithdrawl(TransactionInfo info, String type){
        BigDecimal balance = getBalanceAmount(info.getCustomer_id(),info.getAccount_no());
        if(Helper.idCheck(info.getCustomer_id()).equals("Invalid Customer_id")) {
        	return "Deactivated Accout.....!\nTransaction Cancelled";
        }
        int rate =balance.compareTo(info.getAmount());
        if(rate>=0) {
            BigDecimal totalAmount = balance.subtract(info.getAmount());
            int status = db.transcation(info, totalAmount);
            if(status>0) {
                Helper.transData(info, type);
                MapHandler.OBJECT.balanceUpdation(info,totalAmount);
                return "Transaction Completed";
            }else
                return "Transaction Failed...Server Error";
        }
        else
            return "Insufficient Balance";
    }
    public static String amountDeposite(TransactionInfo info, String type){
        BigDecimal balance = getBalanceAmount(info.getCustomer_id(),info.getAccount_no());
        if(balance.equals(-1)) {
        	return "Account Deactivated....Transaction Cancelled";
        }
        BigDecimal totalAmount = balance.add(info.getAmount());
        int rate = db.transcation(info,totalAmount);
        if(rate>0){
            Helper.transData(info,type);
            MapHandler.OBJECT.balanceUpdation(info,totalAmount);
            return "Transaction Completed ";
        }
        else
            return "Transaction Failed...Server Error";
    }
    public static void transData(TransactionInfo info,String type){
        db.transInsertion(info,type);
    }
    public static BigDecimal getBalanceAmount(int customer_id, int account_no){
        HashMap<Integer,HashMap<Integer, AccountInfo>>finalAccountMap=MapHandler.OBJECT.retriveAccountDetails();
        HashMap<Integer, AccountInfo> temp = finalAccountMap.get(customer_id);
        if(temp!=null) {
        AccountInfo info = temp.get(account_no);
        BigDecimal balance = info.getBalance();
        return balance;
        }
        else {
        	return new BigDecimal(-1);
        }
      
    }
    public static String idCheck(int id) {
        if(MapHandler.OBJECT.retriveCustomerDetails().containsKey(id))
            return "valid";
        else
            return "Invalid Customer_id";
    }

    public static String accountCheck(int id,int account_no){
        HashMap<Integer,HashMap<Integer, AccountInfo>>finalAccountMap=MapHandler.OBJECT.retriveAccountDetails();
        HashMap<Integer, AccountInfo> temp = finalAccountMap.get(id);
        if(temp==null){
            return "Deactivated accountNumber";
        }
        if (temp.containsKey(account_no))
            return "valid";
        else return "Invalid Account Number";

    }
    public static String dbClose() throws ExceptionHandling {
        boolean status = db.closingProcess();
        if(status)
            return "your Connection was closed successfully";
        else
            return "Not closed";

    }
    public HashMap<Integer, CustomerInfo> helperThreeCustomer(){
        HashMap<Integer, CustomerInfo>finalCustomerMap=MapHandler.OBJECT.retriveCustomerDetails();
        return finalCustomerMap;
    }
    public HashMap<Integer, AccountInfo> helperThreeAccount(int id){
        HashMap<Integer,HashMap<Integer, AccountInfo>>finalAccountMap=MapHandler.OBJECT.retriveAccountDetails();
        HashMap<Integer, AccountInfo> temp = finalAccountMap.get(id);
            return temp;
    }
}