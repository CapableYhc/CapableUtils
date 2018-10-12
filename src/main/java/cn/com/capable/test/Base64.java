package cn.com.capable.test;

import cn.com.gzheroli.core.util.IdWorker;
import cn.com.gzheroli.core.util.PasswordUtil;

public class Base64 {
    public static void main(String[] args) {
        String content = "";
        String id = IdWorker.getFlowIdWorkerInstance().nextIdStr();
        String pString = PasswordUtil.encrypt("18344535703", "abc@1234",PasswordUtil.getStaticSalt());
        System.out.println(id);
        System.out.println(pString);
        System.out.println(IdWorker.getFlowIdWorkerInstance().nextIdStr());

    }
}
