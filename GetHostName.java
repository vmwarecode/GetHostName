/*
 * ****************************************************************************
 * Copyright VMware, Inc. 2010-2016.  All Rights Reserved.
 * ****************************************************************************
 *
 * This software is made available for use under the terms of the BSD
 * 3-Clause license:
 *
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright 
 *    notice, this list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in 
 *    the documentation and/or other materials provided with the 
 *    distribution.
 * 
 * 3. Neither the name of the copyright holder nor the names of its 
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE 
 * COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS
 * OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
 * USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */


package com.vmware.general;

import com.vmware.common.annotations.Action;
import com.vmware.common.annotations.Sample;
import com.vmware.connection.ConnectedVimServiceBase;
import com.vmware.connection.VCenterSampleBase;
import com.vmware.vim25.InvalidPropertyFaultMsg;
import com.vmware.vim25.ManagedObjectReference;
import com.vmware.vim25.RuntimeFaultFaultMsg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * GetHostName
 *
 * This sample gets the hostname and additional details of the ESX Servers
 * in the inventory
 *
 * <b>Parameters:</b>
 * url          [required] : url of the web service
 * username     [required] : username for the authentication
 * password     [required] : password for the authentication
 *
 * <b>Command Line:</b>
 * run.bat com.vmware.general.GetHostName
 * --url [webservicesurl] --username [username] --password [password]
 * </pre>
 */
@Sample(name = "get-hostname", description = "This sample gets the hostname and additional details of the ESX Servers in the inventory")
public class GetHostName extends VCenterSampleBase {
    private static final List<String> hostSystemAttributesArr =
            new ArrayList<String>();

    public void setHostSystemAttributesList() {
        hostSystemAttributesArr.add("name");
        hostSystemAttributesArr.add("config.product.productLineId");
        hostSystemAttributesArr.add("summary.hardware.cpuMhz");
        hostSystemAttributesArr.add("summary.hardware.numCpuCores");
        hostSystemAttributesArr.add("summary.hardware.cpuModel");
        hostSystemAttributesArr.add("summary.hardware.uuid");
        hostSystemAttributesArr.add("summary.hardware.vendor");
        hostSystemAttributesArr.add("summary.hardware.model");
        hostSystemAttributesArr.add("summary.hardware.memorySize");
        hostSystemAttributesArr.add("summary.hardware.numNics");
        hostSystemAttributesArr.add("summary.config.name");
        hostSystemAttributesArr.add("summary.config.product.osType");
        hostSystemAttributesArr.add("summary.config.vmotionEnabled");
        hostSystemAttributesArr.add("summary.quickStats.overallCpuUsage");
        hostSystemAttributesArr.add("summary.quickStats.overallMemoryUsage");
    }

    /**
     * Prints the Host names.
     *
     * @throws RuntimeFaultFaultMsg
     * @throws InvalidPropertyFaultMsg
     */
    @Action
    public void printHostProductDetails() throws InvalidPropertyFaultMsg, RuntimeFaultFaultMsg {
        setHostSystemAttributesList();
        Map<ManagedObjectReference, Map<String, Object>> hosts =
                getMOREFs.inContainerByType(serviceContent.getRootFolder(),
                        "HostSystem",
                        hostSystemAttributesArr.toArray(new String[]{}));

        for (ManagedObjectReference host : hosts.keySet()) {
            Map<String, Object> hostprops = hosts.get(host);
            for (String prop : hostprops.keySet()) {
                System.out.println(prop + " : " + hostprops.get(prop));
            }
            System.out
                    .println("\n\n***************************************************************");
        }
    }
}