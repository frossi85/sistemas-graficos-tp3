#version 110
#define PI    3.14159265
#define CANTIDAD_LUCES 1

varying vec3 normal;
varying vec3 eyeVec;

varying vec3 lightDir[CANTIDAD_LUCES];
varying float dist[CANTIDAD_LUCES];

void main()
{
	vec4 color = vec4(0.0);
	vec3 N= normalize(normal);
   
	color += (gl_FrontLightModelProduct.sceneColor * gl_FrontMaterial.ambient);
	
	
	for(int i = 0; i < CANTIDAD_LUCES; i++)
	{	
		color += (gl_LightSource[i].ambient * gl_FrontMaterial.ambient);
   
   		vec3 L= normalize(lightDir[i]);
   
   		vec3 D = normalize(-gl_LightSource[i].spotDirection);
   
   		float cos_beta = dot(-L, D);
   
   		float beta = (acos(cos_beta) * 180.0) / PI;
   
   		float spotCutoff = 45.0;    //gl_LightSource[0].spotCutoff
   		float spotExponent = 9.0;  //gl_LightSource[0].spotExponent

   		if(beta > spotCutoff)
   		{
   			//NO esta funcionando el gl_LightSource[0].spotExponent, lo probe
   
   			//float spotEffect = pow(cos_beta, gl_LightSource[i].spotExponent);
   			float spotEffect = pow(cos_beta, spotExponent);

   			//if(gl_LightSource[0].spotExponent < 81.0 && gl_LightSource[0].spotExponent > 79.0)
   			//if(gl_LightSource[0].spotCutoff < 54.0 && gl_LightSource[0].spotCutoff > 56.0)
   			//	spotEffect = 100.0;
   		
   			float att = spotEffect / (gl_LightSource[i].constantAttenuation +
						gl_LightSource[i].linearAttenuation * dist[i] +
						gl_LightSource[i].quadraticAttenuation * dist[i] * dist[i]);
   			
   			float lambertTerm = dot(N,L);
			
			if(lambertTerm > 0.0)
			{
				color += gl_LightSource[i].diffuse * 
			    		 gl_FrontMaterial.diffuse * 
						 lambertTerm * spotEffect;	
						   
				color *= att;	
						   					
				vec3 E = normalize(eyeVec);
				vec3 R = reflect(-L, N);
			
				float specular = pow( max(dot(R, E), 0.0), 
			                 	gl_FrontMaterial.shininess );
			                 
				color += gl_LightSource[i].specular * 
			    		 gl_FrontMaterial.specular * 
						 specular * spotEffect;	
						 
			    //color += gl_FrontMaterial.emission;
			}
   		}
   	}
   	
   	//Sumo la emisividad del material al color;
   	color += gl_FrontMaterial.emission;
   
	gl_FragColor = color;	
}
