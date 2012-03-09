#version 110
#define PI    3.14159265

varying vec3 normal;
varying vec3 lightDir;
varying float Dist;

varying vec3 eyeVec, spotDir;

void main()
{
   vec4 color =
				(gl_FrontLightModelProduct.sceneColor * gl_FrontMaterial.ambient) +
				(gl_LightSource[0].ambient * gl_FrontMaterial.ambient);
	
   //vec3 normal= normalize(Normal);
   //vec3 lightDir= normalize(LightDir);
   vec3 N= normalize(normal);
   vec3 L= normalize(lightDir);
   
   vec3 D = normalize(-gl_LightSource[0].spotDirection);
   
   float cos_beta = dot(-L, D);
   
   float beta = (acos(cos_beta) * 180.0) / PI;
   
   //if(beta < gl_LightSource[0].spotCutoff)
   //{
   		//float spotEffect = pow(cos_beta, gl_LightSource[0].spotExponent);
   		float spotEffect = 100.0;
   		
   		float lambertTerm = dot(N,L);
			
		if(lambertTerm > 0.0)
		{
			color += gl_LightSource[0].diffuse * 
			               gl_FrontMaterial.diffuse * 
						   lambertTerm;		
						   					
			vec3 E = normalize(eyeVec);
			vec3 R = reflect(-L, N);
			
			float specular = pow( max(dot(R, E), 0.0), 
			                 gl_FrontMaterial.shininess );
			                 
			color += gl_LightSource[0].specular * 
			               gl_FrontMaterial.specular * 
						   specular;	
		}
   //}
   
   gl_FragColor = color;	
}
