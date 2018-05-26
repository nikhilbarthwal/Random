// THIS PART OF THE CODE IS MODIFIED VERSION OF THE CODE
// DEVELOPED BY XIUPING WU FOR HER TOOL LQ-COMPOSER

/**
 * This is the LQNAssemble which assembles sub-models to a system model
 * It uses DOM level 2 and JAXP
 */

import javax.xml.parsers.DocumentBuilderFactory; // import
// javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.*;
import java.util.Vector;

public class xiuping_asm
{

	// private Vector slotsInfo; //elements are objects of Slot

	/**
	 * rename each entry in the sub-model
	 * 
	 * @param subDoc
	 *            is the sub-model document
	 * @param prefix
	 *            is the slot id for that sub-model
	 */
	public void renameSubEntries(Document subDoc, String prefix)
	{
		NodeList entryList = subDoc.getElementsByTagName("entry");

		int entryNum = entryList.getLength();
		for (int i = 0; i < entryNum; i++) // rename all entries
		{
			Node entryNode = entryList.item(i);
			String entryName = ((Element) entryNode).getAttribute("name");
			((Element) entryNode).setAttribute("name", prefix + "_" + entryName);
		}

	} // end of renameSubEntries

	/**
	 * rename each task in the sub-model param subDoc is the sub-model document
	 * param prefix is the slot Id for that sub-model
	 */
	public void renameSubTasks(Document subDoc, String prefix)
	{
		NodeList taskList = subDoc.getElementsByTagName("task");
		int taskNum = taskList.getLength();
		for (int i = 0; i < taskNum; i++) // rename all tasks
		{
			Node taskNode = taskList.item(i);
			String taskName = ((Element) taskNode).getAttribute("name");
			((Element) taskNode).setAttribute("name", prefix + "_" + taskName);
		}

	} // end of renameSubTasks

	/**
	 * rename each non-replaceable processor in the sub-model import all of
	 * processors to the approriate position into the assembly model param
	 * subDoc is the sub-model document param prefix is the slot Id for that
	 * sub-model param assembleDoc is the assembly model document
	 */
	public void importSubProcessors(Document subDoc, String prefix, Document assembleDoc, xiuping_slot curSlot)
	{
		NodeList sub_processorList = subDoc.getElementsByTagName("processor");
		int len = sub_processorList.getLength();
		Vector replaceableProcessors = curSlot.getSourceProcessors();
		Vector targetProcessors = curSlot.getTargetProcessors();

		Node processorNode, clonedProcessor, importedProcessor;
		Element main_root = assembleDoc.getDocumentElement();
		Element sub_root = subDoc.getDocumentElement();
		NodeList systemProcessors = assembleDoc.getElementsByTagName("processor");

		// rename each non-replaceable processors and import them to the assembly model
		for (int i = 0; i < len; i++)
		{
			processorNode = sub_processorList.item(i);

			String processorName = ((Element) processorNode).getAttribute("name");

			// sub-model internal processor rename it and import this processor
			if (!replaceableProcessors.contains(processorName))
			{
				((Element) processorNode).setAttribute("name", prefix + "_" + processorName);
				clonedProcessor = processorNode.cloneNode(true);
				importedProcessor = assembleDoc.importNode(clonedProcessor, true);
				importedProcessor.normalize();
				main_root.appendChild(importedProcessor);
			}
			else // replaceable processor replace it with the system processor imports all of its children to assemble model
			{
				int index = replaceableProcessors.indexOf(processorName);
				String targetProcessorName = (String) targetProcessors.elementAt(index);

				int pLen = systemProcessors.getLength();
				for (int j = 0; j < pLen; j++)
				{
					Node targetP = systemProcessors.item(j);
					String targetPName = ((Element) targetP).getAttribute("name");

					// append all children of processorNode in sub-model to the system processor targetP
					if (targetPName.equals(targetProcessorName))
					{
						if (processorNode.hasChildNodes())
						{
							NodeList childOfProcessor = processorNode.getChildNodes();
							int cLen = childOfProcessor.getLength();
							for (int ci = 0; ci < cLen; ci++)
							{
								Node childP = childOfProcessor.item(ci);
								if (childP.getNodeName().equals("processor-params")) continue; // do not import processor-params
								Node clonedChildP = childP.cloneNode(true);
								Node importedChildP = assembleDoc.importNode(clonedChildP, true);
								importedChildP.normalize();
								targetP.appendChild(importedChildP);
							}
						}
						//j = pLen;
					} // end if targetPName
				} // end for j
			} // end if else for replaceable processors
		} // end for each processor renaming

		// remove all processor nodes along with its descendant from sub-model
		/*for (;;)
		{
			sub_processorList = subDoc.getElementsByTagName("processor");
			if (sub_processorList.getLength() > 0)
			{
				processorNode = sub_processorList.item(0);
				sub_root.removeChild(processorNode); // remove all nodes from
				// the sub-model
				// document object
			}
			else
				break; // no processor left in the sub-model
		}
		sub_processorList = subDoc.getElementsByTagName("processor");
		int i = sub_processorList.getLength();
		for (int j=0;j<i;j++)
		{
			processorNode = sub_processorList.item(j);
			sub_root.removeChild(processorNode);
		}*/

	} // end of renameSubProcessors

	/** parameter instantiation for sub-model subDoc */
	public void subParamInstantiate(Document subDoc, xiuping_slot curSlot)
	{
		/** The following is for submodel parameter instantiation */
		Element sub_root = subDoc.getDocumentElement();
		NodeList subSynchCalls = sub_root.getElementsByTagName("synch-call");
		NodeList subASynchCalls = subDoc.getElementsByTagName("asynch-call");
		NodeList forwardCalls = subDoc.getElementsByTagName("forwarding");
		NodeList processorParams = subDoc.getElementsByTagName("processor-params");
		NodeList taskParams = subDoc.getElementsByTagName("task-params");
		NodeList entryParams = subDoc.getElementsByTagName("entry-params");
		NodeList activityParams = subDoc.getElementsByTagName("activity-params");
		Node paraNode;
		Vector paraNameVec = curSlot.getParamNames();
		Vector paraValueVec = curSlot.getParamValues();
		String attrStr;
		boolean hasFound = false;

		for (int i = 0; i < paraNameVec.size(); i++)
		{

			String paraName = (String) paraNameVec.elementAt(i);
			hasFound = false;
			for (int j = 0; j < taskParams.getLength(); j++)
			{
				paraNode = taskParams.item(j);
				attrStr = ((Element) paraNode).getAttribute("mult");
				if (attrStr.equals(paraName))
				{
					((Element) paraNode).setAttribute("mult", (String) paraValueVec.elementAt(i));
					hasFound = true;
					break;
				}
			} // end for j
			if (!hasFound) // search for the next attribute
			{
				for (int j = 0; j < activityParams.getLength(); j++)
				{
					paraNode = activityParams.item(j);
					attrStr = ((Element) paraNode).getAttribute("host-demand-mean");
					if (attrStr.equals(paraName))
					{
						((Element) paraNode).setAttribute("host-demand-mean", (String) paraValueVec.elementAt(i));
						hasFound = true;
						break;
					}
				} // end for j
			} // end if (!hasFound)

			if (!hasFound) // search for the next attribute
			{
				for (int j = 0; j < processorParams.getLength(); j++)
				{
					paraNode = processorParams.item(j);
					attrStr = ((Element) paraNode).getAttribute("multiplicity");
					if (attrStr.equals(paraName))
					{
						((Element) paraNode).setAttribute("multiplicity", (String) paraValueVec.elementAt(i));
						hasFound = true;
						break;
					}
				} // end for j
			} // end if (!hasFound)

			if (!hasFound) // search for the next attribute
			{
				for (int j = 0; j < subSynchCalls.getLength(); j++)
				{
					paraNode = subSynchCalls.item(j);
					attrStr = ((Element) paraNode).getAttribute("calls-mean");
					if (attrStr.equals(paraName))
					{
						((Element) paraNode).setAttribute("calls-mean", (String) paraValueVec.elementAt(i));
						hasFound = true;
						break;
					}
				} // end for j
			} // end if (!hasFound)
			if (!hasFound) // search for the next attribute
			{
				for (int j = 0; j < subASynchCalls.getLength(); j++)
				{
					paraNode = subASynchCalls.item(j);
					attrStr = ((Element) paraNode).getAttribute("calls-mean");
					if (attrStr.equals(paraName))
					{
						((Element) paraNode).setAttribute("calls-mean", (String) paraValueVec.elementAt(i));
						hasFound = true;
						break;
					}
				} // end for j
			} // end if (!hasFound)
			if (!hasFound) // search for the next attribute
			{
				for (int j = 0; j < forwardCalls.getLength(); j++)
				{
					paraNode = forwardCalls.item(j);
					attrStr = ((Element) paraNode).getAttribute("probability");
					if (attrStr.equals(paraName))
					{
						((Element) paraNode).setAttribute("probability", (String) paraValueVec.elementAt(i));
						hasFound = true;
						break;
					}
				} // end for j
			} // end if (!hasFound)
			if (!hasFound) // search for the next attribute
			{
				for (int j = 0; j < entryParams.getLength(); j++)
				{
					paraNode = entryParams.item(j);
					attrStr = ((Element) paraNode).getAttribute("open-arrival-rate");
					if (attrStr.equals(paraName))
					{
						((Element) paraNode).setAttribute("open-arrival-rate", (String) paraValueVec.elementAt(i));
						hasFound = true;
						break;
					}
				} // end for j
			} // end if (!hasFound)
		} // end for i
		/** The above is for parameter instantiation */
	}

	/**
	 * direct a single set of calls from sub-model to the system model
	 * 
	 * @param prefix
	 *            is the slot id
	 * @systemBindPorts are targets for sub-model ports to be bound to. Elements
	 *                  are names of slot ports:String
	 * @subBindPorts are the names of the sub-models ports systemBindPorts[i]
	 *               binds subBindPorts[i]
	 * @param curSlot
	 *            is the currently being processed slot
	 */

	public void directSubSingleCallSet(Document subDoc, NodeList callNodeList, String prefix, Vector systemBindPorts, Vector subBindPorts, xiuping_slot curSlot)
	{
		Node paraNode;
		String dest;
		for (int i = 0; i < callNodeList.getLength(); i++)
		{
			paraNode = callNodeList.item(i);
			dest = ((Element) paraNode).getAttribute("dest");
			int index = subBindPorts.indexOf(dest);
			if (index == -1)	// the destination is in the sub-model, then change the dest to the new named dest
			{
				((Element) paraNode).setAttribute("dest", prefix + "_" + dest);
			}
			else   				// the destination is the out-port
			{
				String outPortName = (String) systemBindPorts.elementAt(index);
				Vector newDestVec = curSlot.getDest2(outPortName);
				String newDest = (String) newDestVec.elementAt(0);
				((Element) paraNode).setAttribute("dest", newDest);
				if (newDestVec.size() > 1) // this call is directed to multiple entries
					for (int j = 1; j < newDestVec.size(); j++)
					{
						newDest = (String) newDestVec.elementAt(j);
						Node clonedNode = paraNode.cloneNode(true);
						Node importedNode = subDoc.importNode(clonedNode, true);
						((Element) paraNode).setAttribute("dest", newDest);
						importedNode.normalize();
						paraNode.getParentNode().appendChild(importedNode);
						i++; // Bypass the newly imported Node
					}
				
			} // end if index==-1
		} // end for i

	} // end of directSubSingleCallSet

	/**
	 * direct calls from sub-model to the system model
	 */
	public void directSubCalls(Document assembleDoc, Document subDoc, xiuping_slot curSlot)
	{
		// the following is for directing calls from the sub-model to the system model
		Vector systemBindPorts = curSlot.getBindTargetPorts();
		Vector subBindPorts = curSlot.getBindSourcePorts();
		String prefix = curSlot.getId();
		NodeList subSynchCalls = subDoc.getElementsByTagName("synch-call");
		NodeList subASynchCalls = subDoc.getElementsByTagName("asynch-call");
		NodeList subForwardCalls = subDoc.getElementsByTagName("forwarding");
		directSubSingleCallSet(subDoc, subSynchCalls, prefix, systemBindPorts, subBindPorts, curSlot);
		directSubSingleCallSet(subDoc, subASynchCalls, prefix, systemBindPorts, subBindPorts, curSlot);
		directSubSingleCallSet(subDoc, subForwardCalls, prefix, systemBindPorts, subBindPorts, curSlot);

	} // end of directSubCalls

	public void directSingleSystemCallSet(Document assembleDoc, NodeList singleCallSet, String slotId, Vector systemBindPorts, Vector subBindPorts, xiuping_slot curSlot)
	{
		Node paraNode;
		String dest;

		for (int i = 0; i < singleCallSet.getLength(); i++)
		{
			paraNode = singleCallSet.item(i);
			dest = ((Element) paraNode).getAttribute("dest");
			int index = dest.indexOf(".");
			String prefix = " ";
			if (index > 0)
			{
				prefix = dest.substring(0, index);
				if (prefix.equals(slotId))
				{
					String portName = dest.substring(index + 1);
					index = systemBindPorts.indexOf(portName);
					String subPortName = (String) subBindPorts.elementAt(index);
					Vector newDestVec = curSlot.getDest1(subPortName);
					String newDest = slotId + "_" + (String) newDestVec.elementAt(0);
					((Element) paraNode).setAttribute("dest", newDest);
					if (newDestVec.size() > 1) // this call is directed to multiple entries
						for (int j = 1; j < newDestVec.size(); j++)
						{
							newDest = slotId + "_" + (String) newDestVec.elementAt(j);
							Node clonedNode = paraNode.cloneNode(true);
							Node importedNode = assembleDoc.importNode(clonedNode, true);
							((Element) paraNode).setAttribute("dest", newDest);
							importedNode.normalize();
							paraNode.getParentNode().appendChild(importedNode);
						}
				} // if prefix=slotId
			} // index > 0
		} // end for i
	} // end of directSingleSystemCallSet

	public void directSystemCalls(Document assembleDoc, xiuping_slot curSlot)
	{
		NodeList systemSynchCalls = assembleDoc.getElementsByTagName("synch-call");
		NodeList systemASynchCalls = assembleDoc.getElementsByTagName("asynch-call");
		NodeList systemForwardCalls = assembleDoc.getElementsByTagName("forwarding");
		Vector systemBindPorts = curSlot.getBindTargetPorts();
		Vector subBindPorts = curSlot.getBindSourcePorts();
		String slotId = curSlot.getId();

		directSingleSystemCallSet(assembleDoc, systemSynchCalls, slotId, systemBindPorts, subBindPorts, curSlot); // check for synchronous calls
		directSingleSystemCallSet(assembleDoc, systemASynchCalls, slotId, systemBindPorts, subBindPorts, curSlot); // check for Asynchronous calls
		directSingleSystemCallSet(assembleDoc, systemForwardCalls, slotId, systemBindPorts, subBindPorts, curSlot); // check for forwading calls

	} // end of directSystemCalls

	/**
	 * method processEachSlot,
	 * 
	 * @param assembleDoc
	 *            is the assembly model document
	 * @param documentBuilder
	 *            is the DocumentBuilder
	 */

	public Document process(Document assembleDoc, xiuping_slot curSlot, Document subDoc) throws Exception
	{
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilderFactory.setNamespaceAware(true);

		String slotId = curSlot.getId();
		NodeList SlotList = subDoc.getElementsByTagName("slot");
		int SlotNum = SlotList.getLength();
		renameSubEntries(subDoc, slotId);							// rename all the entries
		renameSubTasks(subDoc, slotId);								// rename all tasks
		subParamInstantiate(subDoc, curSlot);						// parameter instantiation for the sub-model
		directSubCalls(assembleDoc, subDoc, curSlot);				// direct calls from the sub-model to the system model
		directSystemCalls(assembleDoc, curSlot);					// direct calls from system model to the newly
		importSubProcessors(subDoc, slotId, assembleDoc, curSlot);	// rename each non-replaceable processors and import all of them
		NodeList MainSlotList = assembleDoc.getElementsByTagName("slot");
		int MainSlotNum = MainSlotList.getLength();
		for (int i = 0; i < MainSlotNum; i++)
		{
			Node MainSlotNode = MainSlotList.item(i);
			String tmp = ((Element) MainSlotNode).getAttribute("id");
			if (slotId.equals(tmp))
			{
				MainSlotNode.getParentNode().removeChild(MainSlotNode);
				i = MainSlotNum;
			}
		}

		Element MainNode = assembleDoc.getDocumentElement();

		for (int i = 0; i < SlotNum; i++)
		{
			Node SlotNode = SlotList.item(i);
			Node CloneNode = SlotNode.cloneNode(true);
			Node ImportedNode = assembleDoc.importNode(CloneNode, true);

			ImportedNode.normalize();
			MainNode.appendChild(ImportedNode);
		}

		return assembleDoc;
	} // end of process

} // end of class LQNtest

