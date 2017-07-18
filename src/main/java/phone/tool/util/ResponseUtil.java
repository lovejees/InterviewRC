package phone.tool.util;

import API.Sample.ApiResponse;

public class ResponseUtil {
	
	public static ApiResponse getApiResponse(Object data , Integer status ,Boolean isSucess){
		ApiResponse apiResponse = new ApiResponse();
		apiResponse .setData(data);
		apiResponse.setStatus(status);
		apiResponse.setSuccess(isSucess);
		return apiResponse;
	}

}
