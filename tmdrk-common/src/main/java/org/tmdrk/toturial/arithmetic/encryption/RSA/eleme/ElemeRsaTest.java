package org.tmdrk.toturial.arithmetic.encryption.RSA.eleme;

import java.nio.charset.StandardCharsets;

/**
 * ElemeRsaTest
 *
 * @author Jie.Zhou
 * @date 2020/12/14 19:00
 */
public class ElemeRsaTest {
    private static String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmWdotBquvCYE76voAXrhxGnVtCejJmPfPLhOjMakbb7D8HEWgV4VwPz2WXnaXKIq0dlzuXvrUpQnNkZr/O3+SXvpUDLUVxe7/hF7c7ixeX1qqumJmRSl1Dl/6H8/7yOdX2g1AoRFtVrM7NnjUf/BZ3218h+3gs7MYOYPLaxn6XuwSxgj92SH97TvudlLAQBuSqfqaYbrEwUljc1mc+tKmkAR2kYqryr/NxWXvNbCsz5bXps2RkDL2aGNMM+HwL7XnGhcwOuPYaIw00UAb3xsTP1s5yMM619NVZLoSQJgBGQI0WCsg8R1veFcAhUVv4yMmicwefRMLAuldvYjPES/8QIDAQAB";
    private static String privateKey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCZZ2i0Gq68JgTvq+gBeuHEadW0J6MmY988uE6MxqRtvsPwcRaBXhXA/PZZedpcoirR2XO5e+tSlCc2Rmv87f5Je+lQMtRXF7v+EXtzuLF5fWqq6YmZFKXUOX/ofz/vI51faDUChEW1Wszs2eNR/8FnfbXyH7eCzsxg5g8trGfpe7BLGCP3ZIf3tO+52UsBAG5Kp+pphusTBSWNzWZz60qaQBHaRiqvKv83FZe81sKzPltemzZGQMvZoY0wz4fAvtecaFzA649hojDTRQBvfGxM/WznIwzrX01VkuhJAmAEZAjRYKyDxHW94VwCFRW/jIyaJzB59EwsC6V29iM8RL/xAgMBAAECggEACmjA6w+wcaXmydiRWqBa93fy/1F9OZqnSDh9ZAIKoEPEiekyjL60nS7NDI8/KO/8Fdc6igiFlEnnS0CGDVic26Gj2ERKskozAG9xQA31RPgkp8pHAvMquXbZXg5caT6ybAnnJu+Qx1iM9TlnumWvWvVlxjMuwZqEtcKGnyPGvUoDVz63wPHYlFUcj2+Z1T/P7mxa5QTP3+OeduWkwwc/tP4VNZnk479qc9KdQwkLnNmijjOt7sYPpT5F7zi1Ouk+UUIgm4uCfqfhfFia8jNGXPcIsXBjUC8S5+OnEMt8TbG5z8MoHJTdkLNjXV6cV73LMwXR9UNRCxc24gPuvr0MkQKBgQDWYLKF1jP7ofZfJ/DwnU4ydpFBIbO5UNhx79F9LggQLi1egE7OtUl3+QuzTg54T56wEdayc6WCDueBP+j+2aAaYO8noJGGKJpyAKEFv3olhH0nSpp6o0rQvIFOiIlJgbesS/TllFXPXEXN9Sl3aKnQFVQ9i8VpfBzCQ5L57ps6DQKBgQC3MBtp/lO/u14ldsjKexa8MGjKeaMh1GUYyj8rQ3gch/KRD0vIG3uCGutDCQtC2pmoqUOdafNIrjxeLPagpG4rGUDMuAiiV1itIv+qn4gzxbcJqDcDbNfOWFTXYVc7oXhKtHR6GToj9awhYMv5KOHC3JzHk+cGPXmuzTd7WuoYdQKBgBqEzbCmpbmL+Hke7LwnCWROrHs9GwliidPWUbjFd/PgcnZNMvJ5FISFxz+/+SRC3SN0oXVjvuaOx26CpGTaMObydMMXJslMvoY5s7GNVSPM9tpkbSWci4W0u94mH617IWhr4PiBV/gVtwDaQe3mgZMfAFO4tYvt8XTKfSTzsjoFAoGARUdTRs2hPKSOoVp/61uLqJYiO1ivIi8iILL0GteESD39tXB0d3Q1TGoDyTFVae4kKI4C/ThBgp4qRG96whwNcg013XZIBaVE6w2OZSe6KeI/K87LJ2d1Yhsz0tSEWg/h31bJqhd6TnktpN3npg4Y+0nVnQDJYSnEqCACOIuUdrUCgYB0yCqmym8LFEZFKG2Aw8+DkHzMdq/rW+Mjlchv2dfpXICWA5Ixm8kLY4ebVoaJCAYIBNYnrEBihee6XyXnLmxneSD0LMO3HHP2DIRu79H9eWEO/jJLuAF3HbhI/aw9l1+pzaBVpQSdIsQ5oCZdcTsxvRecWBkbefehqDOIVLJw7A==";
    public static void main(String[] args) throws Exception {
        String eleContent = "appId=CEB_ELEME&body=<p>周杰(先生) 130****1457 <br>天盛广场-A座办公楼9层<br>-<br>小炒鸡丁  x1<br></p>&eleOrderId=5014621336314518669&merchantNo=C20146&nonceStr=ust0q7lm2pp1rezi6bj1pwyjahf29uvf&notifyUrl=https://pay-callback.ele.me/payment_webApi/notify/openAPIPay/7&payAmount=2720&redirectUrl=https://pay-callback.ele.me/payment_webApi/payment/return/7&subject=叁义顺大食堂外卖订单&transactionId=20121470167298429307&uid=ICBC_13011231457";
        String sign = RSA2Util.rsa256Sign(eleContent, privateKey, StandardCharsets.UTF_8.name());
        boolean isOk = RSA2Util.rsa256Check(eleContent, sign, publicKey, StandardCharsets.UTF_8.name());
        System.out.println("isOk="+isOk);


//        privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCCvC2fZ4apBc3wcnr2iWm2ARxm/fh/uipy76hWHUYdga3pMyzdGnqSErTBVwO5vcPbCC0SzDMOnISJCqIsd2is9DGU/s/NpVOksztNYb7HXh9ILe7yntIEGv3Vuwe1zMDNwKY0z2db6jb7gkRmGGhxCdDj6YU2UsmVcs4I1xRiKrzyK2xEY3IdoNDzW09n0mi7BLLERNPcGIizAnBRFO7EUcAJt+p4czMy7JOBN/GMV9+40MvZE/blgG0xqi57dXFWzLaf6JPyzfR6VSeKdZxyxdWS6jIySviturbczTDoNTB3xBy8uOeVrXd06lP1/J8C48f5MiqVSqlUYsEHQ9r9AgMBAAECggEALEsNOgCdQw7QYzKmLnkH874UnWBWnQ+56g8AfYyVgNG61lOcU5zxAiSMUSTmAjwk60St4LyGX+Dvlm6f22jpcg+Z7N0YEph86kQ/R0xikrF078OAPXtJtFJgxdUEG7gvaoZmyWWroxyLXFMaUWzbldC5kT5A/9K0sYmf/KbCJS/PaaZ9K/ZU+kxRZwdCJ7g776suUuIxAETFWwdIIbsmO1fUC6BPjx/5Ubcvi14eBrUFVbeQCmLj7xRhCME/RgXY9q5reu2vfiVcv2Ht0WHtnfHPEr9aGQ2uek5cBLCO2pbBZAtwiw0gRExyVNOeLZE0hgIdLaYgI2bCSXMcdGlmkQKBgQDds/dv6s4PK+OPNmb0RKRuHiT99DzSN3OWFMO8ZPAV4h8BJUk/S8tVanYf1bF7hzw4Q2rl62H2P9RmlfULxSgvSAUUrxglk/C3qmwp5KzGpFTEkJgckqs3kxsniPSENGnM5kCiWF/jMv2UDUj9wW1uNH47olaxT6KdV2fmSkA+5wKBgQCW9aNIF98SalKLKkrQMjuKo808d6nhm+haboFx6onA36NTiUykodizMmO2pldENAVVJyAxoajMX3ogAY42OnZoJRuiYuVu1WxamH3kMtDTOawV5CGydy5m8Z1pYi6wijlJtcx5z1JiT6HWkcJqKSEVVD8MmH2yElcLYuJYC8MOewKBgD6Nd4QJUQhwzqzqoZPMBkTXloJctghuAtvWkf+Gz0KtYSgWmBsWzJEWD6wdxAzhdDk/tFqY1jP/gHNNXi1q/FKS1HQojvyTSDjaCdisH5+QYCZx0alGLK7qf5/hAXSHwU5BrPzhSHS3P1rVgMGwIKsWfoNe42z11iDkt3j/fC8DAoGAGDH2oa9xyXnREQ0OQKBMesQWOQLU75wVeakMhj2ZJpixjMoMxrZKDS6eXL/8bKoJ0MQcnNfUPi1vXYHiUT69i6Om8jf3nfQHEd4vD0w1vCUaN8gAvqHrhgRjvmhGI3WECRRiWYOpKKiqv8FM78b3zntaP6KRQuE9ok2PCZ2Y/dECgYEApBoG4tB2ZguLJnNFDHz6NE1qo1vDIlKM3l2A3qUn+COvpjAG7czmyoOmx5b+t7GWmGa4ULyjVvejCGUHD9ittwwPqxug43uXcQW/FwQwBBDxtP8IX2Rs19z6p1+G9731/tlJIsCEPr8T7xEgDLdRiFYBwkXYmwb76idnnFHg884=";
        String content1 = "appId=CEB_ELEME&body=<p>周杰(先生) 130****1457 <br>天盛广场-A座办公楼9层<br>-<br>小炒鸡丁  x1<br></p>&eleOrderId=5014621336314518669&merchantNo=C20146&nonceStr=ust0q7lm2pp1rezi6bj1pwyjahf29uvf&notifyUrl=https://pay-callback.ele.me/payment_webApi/notify/openAPIPay/7&payAmount=2720&redirectUrl=https://pay-callback.ele.me/payment_webApi/payment/return/7&subject=叁义顺大食堂外卖订单&transactionId=20121470167298429307&uid=ICBC_13011231457";
        String sign1 = RSA2Util.rsa256Sign(content1, privateKey, StandardCharsets.UTF_8.name());

        publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmWdotBquvCYE76voAXrhxGnVtCejJmPfPLhOjMakbb7D8HEWgV4VwPz2WXnaXKIq0dlzuXvrUpQnNkZr/O3+SXvpUDLUVxe7/hF7c7ixeX1qqumJmRSl1Dl/6H8/7yOdX2g1AoRFtVrM7NnjUf/BZ3218h+3gs7MYOYPLaxn6XuwSxgj92SH97TvudlLAQBuSqfqaYbrEwUljc1mc+tKmkAR2kYqryr/NxWXvNbCsz5bXps2RkDL2aGNMM+HwL7XnGhcwOuPYaIw00UAb3xsTP1s5yMM619NVZLoSQJgBGQI0WCsg8R1veFcAhUVv4yMmicwefRMLAuldvYjPES/8QIDAQAB";
        String content2 = "appId=CEB_ELEME&body=<p>周杰(先生) 130****1457 <br>天盛广场-A座办公楼9层<br>-<br>小炒鸡丁  x1<br></p>&eleOrderId=5014621336314518669&merchantNo=C20146&nonceStr=ust0q7lm2pp1rezi6bj1pwyjahf29uvf&notifyUrl=https://pay-callback.ele.me/payment_webApi/notify/openAPIPay/7&payAmount=2720&redirectUrl=https://pay-callback.ele.me/payment_webApi/payment/return/7&subject=叁义顺大食堂外卖订单&transactionId=20121470167298429307&uid=ICBC_13011231457";
        boolean isOk2 = RSA2Util.rsa256Check(content2, sign1, publicKey, StandardCharsets.UTF_8.name());
        System.out.println("isOk2="+isOk2);

    }

}
