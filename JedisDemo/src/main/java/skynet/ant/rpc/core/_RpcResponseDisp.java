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
 * 会话响应（回调）
 **/
public abstract class _RpcResponseDisp extends Ice.ObjectImpl implements RpcResponse
{
    protected void
    ice_copyStateFrom(Ice.Object __obj)
        throws CloneNotSupportedException
    {
        throw new CloneNotSupportedException();
    }

    public static final String[] __ids =
    {
        "::Ice::Object",
        "::skynet::ant::rpc::core::RpcResponse"
    };

    public boolean ice_isA(String s)
    {
        return java.util.Arrays.binarySearch(__ids, s) >= 0;
    }

    public boolean ice_isA(String s, Ice.Current __current)
    {
        return java.util.Arrays.binarySearch(__ids, s) >= 0;
    }

    public String[] ice_ids()
    {
        return __ids;
    }

    public String[] ice_ids(Ice.Current __current)
    {
        return __ids;
    }

    public String ice_id()
    {
        return __ids[1];
    }

    public String ice_id(Ice.Current __current)
    {
        return __ids[1];
    }

    public static String ice_staticId()
    {
        return __ids[1];
    }

    /**
     * 会话结果回调 
     * @param result 响应结果 
     * @return 会话状态
     **/
    public final RpcStatus callback(RpcResult result)
    {
        return callback(result, null);
    }

    public static Ice.DispatchStatus ___callback(RpcResponse __obj, IceInternal.Incoming __inS, Ice.Current __current)
    {
        __checkMode(Ice.OperationMode.Normal, __current.mode);
        IceInternal.BasicStream __is = __inS.startReadParams();
        RpcResult result;
        result = new RpcResult();
        result.__read(__is);
        __inS.endReadParams();
        RpcStatus __ret = __obj.callback(result, __current);
        IceInternal.BasicStream __os = __inS.__startWriteParams(Ice.FormatType.DefaultFormat);
        __ret.__write(__os);
        __inS.__endWriteParams(true);
        return Ice.DispatchStatus.DispatchOK;
    }

    private final static String[] __all =
    {
        "callback",
        "ice_id",
        "ice_ids",
        "ice_isA",
        "ice_ping"
    };

    public Ice.DispatchStatus __dispatch(IceInternal.Incoming in, Ice.Current __current)
    {
        int pos = java.util.Arrays.binarySearch(__all, __current.operation);
        if(pos < 0)
        {
            throw new Ice.OperationNotExistException(__current.id, __current.facet, __current.operation);
        }

        switch(pos)
        {
            case 0:
            {
                return ___callback(this, in, __current);
            }
            case 1:
            {
                return ___ice_id(this, in, __current);
            }
            case 2:
            {
                return ___ice_ids(this, in, __current);
            }
            case 3:
            {
                return ___ice_isA(this, in, __current);
            }
            case 4:
            {
                return ___ice_ping(this, in, __current);
            }
        }

        assert(false);
        throw new Ice.OperationNotExistException(__current.id, __current.facet, __current.operation);
    }

    protected void __writeImpl(IceInternal.BasicStream __os)
    {
        __os.startWriteSlice(ice_staticId(), -1, true);
        __os.endWriteSlice();
    }

    protected void __readImpl(IceInternal.BasicStream __is)
    {
        __is.startReadSlice();
        __is.endReadSlice();
    }

    public static final long serialVersionUID = 0L;
}