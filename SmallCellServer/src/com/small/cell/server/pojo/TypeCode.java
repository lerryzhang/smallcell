package com.small.cell.server.pojo;

public enum TypeCode {

	AuthRequest("0001"), AuthResponse("0002"), ReportRequest("0003"), ReportResponse(
			"0004"), ControlRequest("0005"), ControlResponse("0006"), ConfigureUpdateRequest(
			"0007"), ConfigureUpdateResponse("0008"), EchoRequest("0009"), EchoResponse(
			"000A"), ConfigureQueryRequest("000B"), ConfigureQueryResponse(
			"000C"), AlarmRequest("000D"), AlarmResponse("000E");

	// 定义私有变量
	private String Code;

	private TypeCode(String Code) {

		this.Code = Code;

	}

	public String getCode() {
		return Code;
	}

	public void setCode(String Code) {
		this.Code = Code;
	}

	public static TypeCode getByValue(String value) {
		for (TypeCode code : values()) {
			if (code.getCode().equals(value)) {
				return code;
			}
		}
		return null;
	}

}