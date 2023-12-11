package Api;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.openqa.selenium.By;

import com.dentscribe.common.CommonMethods;

import io.appium.java_client.ios.IOSDriver;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GenerateOTP extends CommonMethods{
 
	// it will the return the OTP after generating it.
	public static char[] getOTP() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phoneNumber", "+91" + readData("Config", "mobile"));
		JSONObject json = new JSONObject(map);
		String otp = null;
		baseURI = "https://api-dev.dentscribe.ai/api/otp/sendSms";
		Response response = given().header("Content-Type", "application/json").contentType(ContentType.JSON).accept(ContentType.JSON).body(json.toJSONString()).when().post(baseURI);
		System.out.println("OTP: "+response.jsonPath().getString("data.otp"));
		otp = response.jsonPath().getString("data.otp");
		char[] ch = otp.toCharArray();
		return ch;
	}
	
	// it will fill the otp in IOS device
	public static void fillOtp(IOSDriver driver, char[] otp) {

		for (int i = 0; i < 6; i++) {
			By OTP = By.xpath("(//XCUIElementTypeTextField[@name='textInput'])[" + (i + 1) + "]");
			sendKeys(driver, OTP, String.valueOf(otp[i]), "OTP :"+(i+1));
		}
	}
	
	// Handling SPU installation and refresh data
	public static void updateOfficeId(String emailId, String officeId) 
	{
        JSONObject request = new JSONObject();
        request.put("email", emailId);
        request.put("officeId", officeId);
        
        Response response = 
                given().
                    header("Content-type", "application/json").
                    contentType(ContentType.JSON).
                    accept(ContentType.JSON).
                    body(request.toString()).
                when().
                    patch("https://api-dev.dentscribe.ai/api/practice/emailId").
                then().
                    extract().response();
        
        System.out.println(response.getStatusCode());
        System.out.println(response.asString());
	}
	
}
