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

public final class _RpcResponseDelM extends Ice._ObjectDelM implements _RpcResponseDel
{
    public RpcStatus
    callback(RpcResult result, java.util.Map<String, String> __ctx, Ice.Instrumentation.InvocationObserver __observer)
        throws IceInternal.LocalExceptionWrapper
    {
        IceInternal.Outgoing __og = __handler.getOutgoing("callback", Ice.OperationMode.Normal, __ctx, __observer);
        try
        {
            try
            {
                IceInternal.BasicStream __os = __og.startWriteParams(Ice.FormatType.DefaultFormat);
                result.__write(__os);
                __og.endWriteParams();
            }
            catch(Ice.LocalException __ex)
            {
                __og.abort(__ex);
            }
            boolean __ok = __og.invoke();
            try
            {
                if(!__ok)
                {
                    try
                    {
                        __og.throwUserException();
                    }
                    catch(Ice.UserException __ex)
                    {
                        throw new Ice.UnknownUserException(__ex.ice_name(), __ex);
                    }
                }
                IceInternal.BasicStream __is = __og.startReadParams();
                RpcStatus __ret;
                __ret = new RpcStatus();
                __ret.__read(__is);
                __og.endReadParams();
                return __ret;
            }
            catch(Ice.LocalException __ex)
            {
                throw new IceInternal.LocalExceptionWrapper(__ex, false);
            }
        }
        finally
        {
            __handler.reclaimOutgoing(__og);
        }
    }
}