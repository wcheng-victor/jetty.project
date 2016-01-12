//
//  ========================================================================
//  Copyright (c) 1995-2015 Mort Bay Consulting Pty. Ltd.
//  ------------------------------------------------------------------------
//  All rights reserved. This program and the accompanying materials
//  are made available under the terms of the Eclipse Public License v1.0
//  and Apache License v2.0 which accompanies this distribution.
//
//      The Eclipse Public License is available at
//      http://www.eclipse.org/legal/epl-v10.html
//
//      The Apache License v2.0 is available at
//      http://www.opensource.org/licenses/apache2.0.php
//
//  You may elect to redistribute this code under either of these licenses.
//  ========================================================================
//

package org.eclipse.jetty.server.session.remote;

import org.eclipse.jetty.server.session.AbstractLastAccessTimeTest;
import org.eclipse.jetty.server.session.AbstractSessionManager;
import org.eclipse.jetty.server.session.AbstractTestServer;
import org.eclipse.jetty.server.session.InfinispanTestSessionServer;

import static org.junit.Assert.assertEquals;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public class RemoteLastAccessTimeTest extends AbstractLastAccessTimeTest
{ 
   public static RemoteInfinispanTestSupport __testSupport;
   
    
    @BeforeClass
    public static void setup () throws Exception
    {
       __testSupport = new RemoteInfinispanTestSupport("remote-session-test");
       __testSupport.setup();
    }
    
    @AfterClass
    public static void teardown () throws Exception
    {
       __testSupport.teardown();
    }
    

    @Override
    public AbstractTestServer createServer(int port, int max, int scavenge)
    {
        return new InfinispanTestSessionServer(port, max, scavenge, __testSupport.getCache());
    }

    @Override
    public void testLastAccessTime() throws Exception
    {
        super.testLastAccessTime();
    }

  
    @Override
    public void assertSessionsAfterCreation (AbstractSessionManager m)
    {
        assertSessions(1,1,1, m);
    }
    
    public void assertSessions (int count, int max, int total, AbstractSessionManager m)
    {
        assertEquals(count, m.getSessions());
        assertEquals(max, m.getSessionsMax());
        assertEquals(total, m.getSessionsTotal());
    }
    
    @Override
    public void assertSessionsAfterScavenge(AbstractSessionManager m)
    {
        //the InfinispanSessionManager will throw a session out of memory if it is checked
        //against the cluster during scavenge and found to be managed by another node
        assertSessions(0, 1, 1, m);
    }
    
}