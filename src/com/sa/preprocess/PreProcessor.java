package com.sa.preprocess;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class PreProcessor
{
    static private int sentEvents = 0;
    
    // static public EventBean[] test()
    
    static public void main(String[] args) {

        //イベント名とイベントクラスのマッピング定義
        Configuration config = new Configuration();
        config.addEventType("SampleEvent", SampleEvent.class);
    
        EPServiceProvider serv = EPServiceProviderManager.getDefaultProvider(config);

        //EPL から EPStatement オブジェクトを生成
        String epl = "select id, count(count) as num, sum(count) as total, avg(count) as average from SampleEvent.win:time_batch(5 sec) group by id";
        EPStatement st = serv.getEPAdministrator().createEPL(epl);

        //String epl2 = "select * from Summary";
        //EPStatement st2 = serv.getEPAdministrator().createEPL(epl2);

        //イベントリスナーの設定
        st.addListener(new UpdateListener() {

            @Override
            public void update(EventBean[] newEvents, EventBean[] oldEvents) {
                try{
                    System.out.println("######################  call update ##################");
                    if (newEvents != null && newEvents.length > 0) {
                        for(int i = 0 ; i < newEvents.length ; i++) {
                            System.out.println("event : " + 
                                    " id=" + newEvents[i].get("id") + 
                                    " total=" + newEvents[i].get("total") +
                                    " num=" + newEvents[i].get("num") +
                                    " average=" + newEvents[i].get("average") +
                                    " events:" + sentEvents);
                            sentEvents--;
                            System.out.println("sentEvents:" + sentEvents);
                        }
                    }

                }catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });

        for(int x = 0 ; x < 10 ; x++) {
            //イベント送信
            for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 3;j++) {
                int val = (int)(Math.random() * 10);
                //ランダムな値を設定したイベント送信
                serv.getEPRuntime().sendEvent(new SampleEvent(i, val, "test" + i));
            }
            sentEvents++;
            }
            while(sentEvents != 0) {
                try{
                    Thread.sleep(1000);
                }catch(Exception e) {
                    System.out.println("ERRROR" + e.getMessage());
                }
            }
        }
    }
}