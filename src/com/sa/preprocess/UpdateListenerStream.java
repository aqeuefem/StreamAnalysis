package com.sa.preprocess;

import java.util.concurrent.LinkedBlockingQueue;

import weka.core.DenseInstance;
import weka.core.Instance;
import moa.MOAObject;
import moa.core.InstancesHeader;
import moa.streams.InstanceStream;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class UpdateListenerStream implements UpdateListener, InstanceStream
{
    private LinkedBlockingQueue<Instance> eventQueue_ = new LinkedBlockingQueue<Instance>();
    
    @Override
    public void update(EventBean[] arg0, EventBean[] arg1) {
        // Vectorization and Instance
        int numAttribute = 5;
        DenseInstance instance = new DenseInstance(numAttribute);
        try {
            eventQueue_.put(instance);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public long estimatedRemainingInstances() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public InstancesHeader getHeader() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean hasMoreInstances() {
        return !eventQueue_.isEmpty();
    }

    @Override
    public boolean isRestartable() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Instance nextInstance() {
        // Blocking wait for input a event
        return eventQueue_.poll();
    }

    @Override
    public void restart() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public MOAObject copy() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void getDescription(StringBuilder arg0, int arg1) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public int measureByteSize() {
        // TODO Auto-generated method stub
        return 0;
    }
}
