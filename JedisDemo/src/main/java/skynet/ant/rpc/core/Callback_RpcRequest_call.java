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
 * 通用远程方法
 **/

public abstract class Callback_RpcRequest_call extends Ice.TwowayCallback
{
    public abstract void response(byte[] __ret);

    public final void __completed(Ice.AsyncResult __result)
    {
        RpcRequestPrx __proxy = (RpcRequestPrx)__result.getProxy();
        byte[] __ret = null;
        try
        {
            __ret = __proxy.end_call(__result);
        }
        catch(Ice.LocalException __ex)
        {
            exception(__ex);
            return;
        }
        response(__ret);
    }
}
