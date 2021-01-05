package genDataNOapplication;

import genDataNOapplication.RV.RV;
import genDataNOapplication.model.ConfigurationModel;

// Copyright (C) 2018  David F. Nettleton (david.nettleton@upf.edu)
// License: GNU GENERAL PUBLIC LICENSE v3.0   See LICENSE for the full license.

/**
 * @author  David Nettleton
 * @created Nov 28th, 2012
 * @last modified 	April 30th, 2018
 */
public class GenDataNO
{
	public static int main(ConfigurationModel configuration)
	{
	   configuration.loadCommunitiesFrequencies();
	   RV.resetStaticValues();
	   RV rv = new RV();
	   return rv.RVp(configuration);
	}
}
