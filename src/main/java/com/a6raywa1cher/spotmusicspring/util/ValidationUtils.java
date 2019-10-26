package com.a6raywa1cher.spotmusicspring.util;

/**
 * ValidationUtils
 * Utils to validate various fields
 *
 * @author 6rayWa1cher
 */
public class ValidationUtils {
	private static final String TEL_REGEX = "^(\\+?\\d{1,3}[- ]?)?\\d{10}(\\[\\d+])?$";
	private static final String EMAIL_REGEX = ".+@.+";

	public static boolean isCheckingAccountValid(String checking_account) {
		if (checking_account == null) return false;
		return checking_account
				.trim()
				.replaceAll("\\D", "")
				.matches("[0-9]{20}");
	}

	public static boolean isTaxpayerIdNumValid(String taxpayer_id_num) {
		if (taxpayer_id_num == null) return false;
		taxpayer_id_num = taxpayer_id_num.trim();
		if (!taxpayer_id_num.matches("[0-9]{10,12}")) {
			return false;
		}
		long sum = 0;
		int[] validationArray;
		switch (taxpayer_id_num.length()) {
			case 10:
				validationArray = new int[]{
						2, 4, 10, 3, 5, 9, 4, 6, 8
				};
				break;
			case 11:
				validationArray = new int[]{
						7, 2, 4, 10, 3, 5, 9, 4, 6, 8
				};
				break;
			case 12:
				validationArray = new int[]{
						3, 7, 2, 4, 10, 3, 5, 9, 4, 6, 8
				};
				break;
			default:
				return false;
		}
		for (int i = 0; i < validationArray.length; i++) {
			sum += Character.digit(taxpayer_id_num.charAt(i), 10) * validationArray[i];
		}
		if ((sum % 11) % 10 != Character.digit(taxpayer_id_num.charAt(taxpayer_id_num.length() - 1), 10)) {
			return false;
		}
		return true;
	}

	public static boolean isReasonRegCodeValid(String reason_reg_code) {
		if (reason_reg_code == null) return false;
		return reason_reg_code
				.trim()
				.replaceAll("-", "")
				.matches("\\d{4}[a-zA-Z0-9]{2}\\d{3}");
	}

	public static boolean isBankIdCodeValid(String bank_id_code) {
		if (bank_id_code == null) return false;
		bank_id_code = bank_id_code.trim();
		if (!bank_id_code.matches("04[0-9]{7}")) {
			return false;
		}
		int lastnums = Integer.parseInt(bank_id_code.substring(6));
		if ((lastnums != 0 && lastnums != 1 && lastnums != 2) && lastnums < 50) {
			return false;
		}
		return true;
	}

	public static boolean isEmailValid(String email) {
		if (email == null) return false;
		return email.trim().matches(EMAIL_REGEX);
	}

	public static boolean isTelValid(String tel) {
		if (tel == null) return false;
		return normalizeTel(tel).matches(TEL_REGEX);
	}

	public static boolean isViberIdValid(String viber_id) {
		return isTelValid(viber_id);
	}

	public static boolean isGovRegNumValid(String gov_reg_num) {
		if (gov_reg_num == null) return false;
		gov_reg_num = gov_reg_num.trim().replaceAll("\\D", "");
		return gov_reg_num.matches("[0-9]{13}") &&
				(Long.parseLong(gov_reg_num.substring(0, gov_reg_num.length() - 1)) % 11) % 10 ==
						Character.digit(gov_reg_num.charAt(gov_reg_num.length() - 1), 10);
	}

	public static boolean isCorrespondentNumberValid(String correspondent_number) {
		if (correspondent_number == null) return false;
		return correspondent_number.trim().matches("301[0-9]{17}");
	}

	public static boolean isFileNameCorrect(String pic_name) {
		if (pic_name == null) return false;
		return pic_name
				.trim()
				.matches("[0-9a-zA-Z-]+\\.[a-z]{3,4}");
	}

	public static String normalizeTel(String tel) {
		if (tel != null) {
			if (tel.charAt(0) == '8') tel = "7" + tel.substring(1);
			tel = tel.replaceAll("[+()\\- ]", "")
					.trim();
		}
		return tel;
	}
}
