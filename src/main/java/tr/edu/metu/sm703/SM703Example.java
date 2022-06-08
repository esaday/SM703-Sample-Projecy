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

        PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(output, Charset.forName("US-ASCII"))));
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            Object obj = JSONValue.parse(reader);
            JSONObject event = (JSONObject)obj;

            String bodyString = (String)event.get("body");

            JSONObject body = (JSONObject)JSONValue.parse(bodyString);
            Integer o1 = ((Number)body.get("o1")).intValue();
            Integer o2 = ((Number)body.get("o2")).intValue();

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