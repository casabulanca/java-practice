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

public final class RpcResponseHolder extends Ice.ObjectHolderBase<RpcResponse>
{
    public
    RpcResponseHolder()
    {
    }

    public
    RpcResponseHolder(RpcResponse value)
    {
        this.value = value;
    }

    public void
    patch(Ice.Object v)
    {
        if(v == null || v instanceof RpcResponse)
        {
            value = (RpcResponse)v;
        }
        else
        {
            IceInternal.Ex.throwUOE(type(), v);
        }
    }

    public String
    type()
    {
        return _RpcResponseDisp.ice_staticId();
    }
}