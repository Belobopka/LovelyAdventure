package lovely.Adventure;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import lovely.game2.R;

public class TopResults {
    static Context context;
    static ArrayList<String> oldResults = new ArrayList<String>();
    static String filePath;
    static File file;
    static int check = 0;

    public static void topResultsCreator(Context contexto) throws IOException {
        if(check == 0) {
            context = contexto;
            filePath = context.getFilesDir().getAbsolutePath();
            file = new File(filePath, "topresults");
            if (!file.exists()) {
                file.createNewFile();
            }
            oldResults = resultsReader();
            if (oldResults.size() == 0) {
                oldResults.add("0");
            }
            check = 1;
        }
    }
    public static void setNewResult(int result)throws IOException {
        if (result > 0 ) {
            if (Integer.valueOf(oldResults.get(0)) <= result) {
                ArrayList<String> NewResults = createNewResults(result);
                BinaryInsertionSortArrayList(NewResults, NewResults.size());
                if (NewResults.size() > 11) {
                    NewResults.remove(11);
                }
                oldResults = NewResults;
                resultsWriter(oldResults);
            }
        }
    }
    public static void resultsWriter(ArrayList<String> arrayList) throws IOException {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter((new FileWriter(file)));
            for(String s:arrayList){
                bufferedWriter.write(s);
                bufferedWriter.write("\n");
            }
            bufferedWriter.close();
        } catch (IOException e) {
        }

    }

    public static ArrayList<String> resultsReader() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        ArrayList<String> results = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            results.add(line);
            System.out.println(line);
        }
        br.close();
        return results;
    }
    public static ArrayList<String> arrayToArrayList(int[] a){
        ArrayList<String> arrayList = new ArrayList<String>();
        for(int i : a){
            arrayList.add(String.valueOf(i));
        }
        return  arrayList;
    }
    public int[] arrayListToArray(ArrayList<String> arrayList){
        int [] array = new int[arrayList.size()];
        for(int i = 0 ; i <= arrayList.size();i++){
            array[i] = Integer.valueOf(arrayList.get(i));
        }
        return  array;
    }
    public static ArrayList<String>  createNewResults(int result) throws IOException {
        ArrayList<String> NewResults;
        NewResults = oldResults;
        NewResults.add(String.valueOf(result));
        return NewResults;
    }
    static void resultsCleaner(){
        try {
            BufferedWriter bufferedWriter = new BufferedWriter((new FileWriter(file)));
            bufferedWriter.write("");
            bufferedWriter.close();
            oldResults = resultsReader();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
   static void BinaryInsertionSortArrayList (ArrayList<String> a, int n)
    {
        int ins, i, j;
        String tmp;
        for (i = 1; i < n; i++) {
            ins = BinarySearchArrayList (a, 0, i, a.get(i));
            if (ins < i) {
                tmp = a.get(i);
                for (j = i - 1; j >= ins; j--)
                    a.set(j + 1,a.get(j));
                a.set(ins,tmp);
            }
        }
    }
    static int BinarySearchArrayList (ArrayList<String> a, int low, int high, String skey) {
        int mid;
        int key = Integer.valueOf(skey);

        if (low == high) return low;
        mid = low + ((high - low) / 2);

        if (key > Integer.valueOf(a.get(mid))) return BinarySearchArrayList (a, mid + 1, high, skey);

        else if (key < Integer.valueOf(a.get(mid))) return BinarySearchArrayList (a, low, mid, skey);

        return mid;
    }
    static ArrayList<TextView> createAndSetTextViewsArray(Activity view){
        ArrayList<TextView> textViewsArray = new ArrayList<>();
        textViewsArray.add( (TextView) view.findViewById(R.id.textView3));
        textViewsArray.add( (TextView) view.findViewById(R.id.textView4));
        textViewsArray.add( (TextView) view.findViewById(R.id.textView5));
        textViewsArray.add( (TextView) view.findViewById(R.id.textView6));
        textViewsArray.add( (TextView) view.findViewById(R.id.textView7));
        textViewsArray.add( (TextView) view.findViewById(R.id.textView8));
        textViewsArray.add( (TextView) view.findViewById(R.id.textView9));
        textViewsArray.add( (TextView) view.findViewById(R.id.textView10));
        textViewsArray.add( (TextView) view.findViewById(R.id.textView11));
        textViewsArray.add( (TextView) view.findViewById(R.id.textView12));
        try {
            TopResults.topResultsCreator(view);
            for(int i = 0; i <= TopResults.oldResults.size() - 1;i++){
                textViewsArray.get(i).setText( i + 1 + ")" + TopResults.oldResults.get(
                        TopResults.oldResults.size() - i - 1));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  textViewsArray;
    }
}