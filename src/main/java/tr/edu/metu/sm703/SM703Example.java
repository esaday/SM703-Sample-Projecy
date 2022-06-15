package tr.edu.metu.sm703;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import org.json.simple.*;
import static java.util.Objects.isNull;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;


public class SM703Example implements RequestStreamHandler {

        public boolean checkEquals(Integer o1, Integer o2){
        if (isNull(o1))
            return false; // we assume null's are not equal
        return o1.equals(o2);
    }

    @Override
    public void handleRequest(InputStream input, OutputStream output, Context context) {
        Integer o1 = null, o2 = null;
        JSONObject errorObject = null;
        PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(output, Charset.forName("US-ASCII"))));
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            Object obj = JSONValue.parse(reader);
            JSONObject event = (JSONObject)obj;

            if(event.containsKey("o1") && event.containsKey("o2")){
                o1 = ((Number)event.get("o1")).intValue();
                o2 = ((Number)event.get("o2")).intValue();
            }
            else{
                String bodyString = (String)event.get("body");
                JSONObject body = (JSONObject)JSONValue.parse(bodyString);
                if(body.containsKey("o1") && body.containsKey("o2") ){
                    o1 = ((Number)body.get("o1")).intValue();
                    o2 = ((Number)body.get("o2")).intValue();
                }
            }

            
            if(isNull(o1) && isNull(o2)){
                errorObject = new JSONObject();
                errorObject.put("statusCode", 400);
                errorObject.put("isBase64Encoded", false);
                errorObject.put("headers", null);
                errorObject.put("body", "Invalid request. You need to feed atleast o1 or o2 value!");
                writer.write(errorObject.toString());
                writer.close();
                return;
            }

        
        JSONObject responseObject = new JSONObject();

        responseObject.put("statusCode", 200);
        responseObject.put("isBase64Encoded", false);
        responseObject.put("headers", null);


        JSONObject responseBody = new JSONObject(); 
        responseBody.put("message", Boolean.toString(checkEquals(o1,o2)));
        responseObject.put("body", checkEquals(o1,o2) ? "Vaules are equal" : "Values are NOT equal");
        
        System.out.println("Response object is : " + responseObject.toJSONString());
        writer.write(responseObject.toString());
        writer.close();
    }
}