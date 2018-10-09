package com.dengqinghua.example;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class SignatureTest {
    static Map<String, String> toSign = new HashMap<>();
    static Map<String, String> toSign1 = new HashMap<>();
    static Map<String, String> toSign2 = new HashMap<>();

    static String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMKZkwy3qVV+JHww0AWJzk7xR/LPRZem9LmCwMBTExGZWSynOZw1r79aUpZUifaJW+bXGpPBN8o4I0Eu1U1sJzur89BSQxY/NDLEHRn9HDg16De6xoCYV3wMuzzpXXtbwMZOmbgHoBrBQ7BMtV+3Gs3xXSJy8qvf5slkrgVVDMnlAgMBAAECgYA14NFO5xbSHc+6NsWRkvr07mbOOkb5WvdM2X/Gf/m6mKuQ6mfP8SlMoJRXHObpxFQWkJ3CJO/uZCaWqIHUIsqlhVaL4OrY52UntZCarzupLvY+QWMC5lO48UZwfRubqBXYqD+OrNfq3c+4NN/W75Q+pGhtw/SiNIRN+H4TZGGNbQJBAPuOrXS/nqbpBqhpHNN17ZDkZ35cNVS9PBPovWhhMjae9lvv5116BjLSo6fwThyH0ba7zMKUasuUsITv0VHY1bcCQQDGCWKWT7vK1GemfBK5Du3Qxg6hGrQZnuqAlWlKC5xnb21bGyZ7PlZHKCZwmH0FQrfvMNEBq5S9txC42+56of1DAkBNaq51FJe11FcpMxgc99kB01kKaUQ8bPR1SBYqbmcuqiM1ThKNEQWhVHPrNihD3YSr0QYGB/kJ6Bue/dMUdj/nAkEAv5K4ojvDqLTNCAqieg7tE7dk04hnjRlkNLtUvWJ6DL2IBkg/c52cDP3UIbwBxSMDmAmbRohbPSNos8td6ZfmzQJAGPJQiA6ibEeoXEEYKDsifUmgsUS/XjP7A5eN1kiw+tTLJyojD2ZBHm7unftvGcJLw6ppsw/LimGwJbDniass/Q==";

    static String publicKey  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDCmZMMt6lVfiR8MNAFic5O8Ufyz0WXpvS5gsDAUxMRmVkspzmcNa+/WlKWVIn2iVvm1xqTwTfKOCNBLtVNbCc7q/PQ UkMWPzQyxB0Z/Rw4Neg3usaAmFd8DLs86V17W8DGTpm4B6AawUOwTLVftxrN8V0icvKr3+bJZK4FVQzJ5QIDAQAB";

    @BeforeClass public static void setToSigns() {
        toSign.put("trace_id", "1537520170802");
        toSign.put("amount", "1");
        toSign.put("merchant_create_time", "20180921165610");
        toSign.put("sign_sn", "000000");
        toSign.put("subject", "北京不知名公司");
        toSign.put("detail_type", "99");
        toSign.put("remark", "测试对公对私代发或者提现");
        toSign.put("merchant_id", "1000006049");
        toSign.put("version", "1.0");
        toSign.put("noncestr", "1537520170802");
        toSign.put("card_id", "72057594038745244");
        toSign.put("out_trade_no", "123_1537520170802");
        toSign.put("card_no_suffix", "6032");
        toSign.put("currency", "CNY");
        toSign.put("timestamp", "1537520170802");

        toSign1.put("content", "{\"orderId\":\"20161213181444\",\"subCode\":\"0002\",\"subMsg\":\"该订单不存在\",\"transDate\":\"20161213182347\"}");
        toSign1.put("merId", "8088000000000001");
        toSign1.put("code", "0000");
        toSign1.put("msg", "查询成功");

        toSign2.put("a", "1");
    }

    @Test public void sign() throws Exception {
        Signature s = new Signature(privateKey, publicKey);
        assertThat(s.sign(toSign), is("H02s3NQfgtth5chowfXNrZxjKbJGrI73NJgS5/RXsQJ1ftmctFd3ZSlSw+jHCoWA43p4FsVZfFxSlXq7ZODBag4c26ipfVHd0W9NqLcoxJHZB6fKNDnuQuipgPfiAsy9FuGNA7MpkY+16aqg9UgH9meD9aVM0l0tWqWtjEkuh58="));
    }

    @Test public void sign1() throws Exception {
        Signature s = new Signature(privateKey, publicKey);
        String sign = s.sign(toSign1);
        assertThat(sign, is("TQ0Tvhog/JbDgZSfKFiIGJRi3T9t1DRvz/Om7R9jPoUxoWQGTczlEmJK66U6mT3Z7ipvrIQd0y1T6QtWxeoDstvEkuNP3VvMDsXpkVS8gl1LwS1OXJuGCrSlAlES/y18M5Bz4qj7bCP+ZzOT04WVchNDzG2jR1ZYu4QJ1AL2DjU="));

        toSign1.put("sign", sign);
        assertThat(s.verify(toSign1), is(true));
    }

    @Test public void sign2() throws Exception {
        Signature s = new Signature(privateKey, publicKey);
        assertThat(s.sign(toSign2), is("cAROb4OBHuvcizCVLq1acBfKAvZj9MstgJOrATtgCfpKGyS/MfEL+m4X4FM/MJL8nFZA7k1qG3H9HUHbbTqFcowGlz3pO3KtF7gccVDY5GvYvzRx47PfgnTX7nE2lkLJV9jIG3rg3ylfIA3cuA9x3eq++HfainC3nuZwjxtsar4="));
    }
}
