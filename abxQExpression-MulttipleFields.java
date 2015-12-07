 // in our example, we search with 5 fields 
	int N = 5;

    public  void sampleAbx() {
        //preparing parameters
        String containerName = "sampleContainer";
		
		// create a list for qFields
        ArrayList<AbxQField> qFields = new ArrayList<>();
	
		// Search the values for N fields
        for (int indexFields = 0; indexFields < N; indexFields++) {
            AbxQField qField = new AbxQField();
            qField.setFieldName("name" + indexFields);
            qField.setFOperator("=");
            qField.setValue("%searchValue" + indexFields + "%";
            qField.setFieldTypeName("testo");
            qFields.add(qField);
        }


        //prepare AbxQexpression
        AbxQexpression vExpression = GetExpression(containerName, qFields);
        AbxOption4Query options = new AbxOption4Query();
        options.setDeepLevel(1);
        options.setRootContainerName(containerName);
        vExpression.setOption4Query(options);
        vExpression.setQueryType("all");
    }
 
 public AbxQexpression GetExpression(String containerName, ArrayList<AbxQField> qFields) {
        AbxQnode abxQnode = null;
        AbxQexpression abxQexpression = new AbxQexpression();
		// Check if the qFields list is empty
        if (qFields == null || qFields.isEmpty()) {
            return abxQexpression;
        }

		// For each qField from the list set the abxQnode
        for (int i = (qFields.size() - 1); i >= 0; i--) {
            abxQnode = ExploreNode((AbxQField) qFields.get(i), abxQnode);
        }
		
		// add the abxQnode to the expression
        abxQexpression.setExpression(abxQnode);
        abxQexpression.setQueryLimit("100"); //option to set query limit
        return abxQexpression;
    }
	
	 private AbxQnode ExploreNode(AbxQField abxQField, AbxQnode abxQnode) {
        AbxQnode abxQnodePvt;
        AbxQnode abxQnodePvtRet;

        if (abxQField != null) {
			// Set the LIKE operator for "%value%" values
            if (abxQField.getValue().startsWith("%") || abxQField.getValue().endsWith("%")) {
                abxQField.setFOperator("LIKE");
            } else {
                abxQField.setFOperator("=");
            }
			// if the abxQnode is empty - create a new abxQnode and add the abxQField
            if (abxQnode == null) {
                abxQnodePvtRet = new AbxQnode();
                abxQnodePvtRet.setField(abxQField);
            } else {
			// if abxQnode is not empty - add a new abxQfield and call multiple operands
                abxQnodePvt = new AbxQnode();
                abxQnodePvt.setField(abxQField);
                abxQnodePvtRet = new AbxQnode();
                abxQnodePvtRet.setOperandDX(abxQnodePvt);
                abxQnodePvtRet.setOperandSX(abxQnode);
                abxQnodePvtRet.setQOperator("AND");
            }
        } else {
            abxQnodePvtRet = abxQnode;
        }
        return abxQnodePvtRet;
    }
