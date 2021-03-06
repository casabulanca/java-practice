// **********************************************************************
//
// Copyright (c) 2003-2013 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************
//
// Ice version 3.5.1
//
// <auto-generated>
//
// Generated from file `RpcChannel.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package skynet.ant.rpc.core;

/**
 * 会话结果回调 
 **/

public abstract class Callback_RpcResponse_callback extends Ice.TwowayCallback
{
    public abstract void response(RpcStatus __ret);

    public final void __completed(Ice.AsyncResult __result)
    {
        RpcResponsePrx __proxy = (RpcResponsePrx)__result.getProxy();
        RpcStatus __ret = null;
        try
        {
            __ret = __proxy.end_callback(__result);
        }
        catch(Ice.LocalException __ex)
        {
            exception(__ex);
            return;
        }
        response(__ret);
    }
}
