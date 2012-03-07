varying vec4 diffuse,ambientGlobal, ambient;
varying vec3 normal,lightDir,halfVector;
varying float dist;

void main()
{
	vec3 n,halfV,viewV,ldir;
	float NdotL,NdotHV;
	vec4 color = ambientGlobal;
	float att;
	/* a fragment shader can't write a varying variable, hence we need

	a new variable to store the normalized interpolated normal */
	n = normalize(normal);
	/* compute the dot product between normal and normalized lightdir */

	NdotL = max(dot(n,normalize(lightDir)),0.0);
	if (NdotL > 0.0) {
	
		float spotEffect = dot(normalize(gl_LightSource[0].spotDirection),normalize(-lightDir));
		
		if (spotEffect > gl_LightSource[0].spotCosCutoff) {
			spotEffect = pow(spotEffect, gl_LightSource[0].spotExponent);
			att = spotEffect / (gl_LightSource[0].constantAttenuation +
				gl_LightSource[0].linearAttenuation * dist +
				gl_LightSource[0].quadraticAttenuation * dist * dist);

			color += att * (diffuse * NdotL + ambient);

			halfV = normalize(halfVector);
			NdotHV = max(dot(n,halfV),0.0);
			color += att * gl_FrontMaterial.specular *
			    	gl_LightSource[0].specular *
			    	pow(NdotHV,gl_FrontMaterial.shininess);

		}

		/*att = 1.0 / (gl_LightSource[0].constantAttenuation +
				gl_LightSource[0].linearAttenuation * dist +
				gl_LightSource[0].quadraticAttenuation * dist * dist);
		color += att * (diffuse * NdotL + ambient);
		halfV = normalize(halfVector);

		NdotHV = max(dot(n,halfV),0.0);
		color += att * gl_FrontMaterial.specular * gl_LightSource[0].specular *
						pow(NdotHV,gl_FrontMaterial.shininess);*/
	}
	gl_FragColor = color;
}

/*uniform sampler2D tex;

uniform bool esTextura2D;

//Variables para cube Map
uniform samplerCube cubeMap;
varying vec3 vTexCoord;
uniform bool esCubeMap;

uniform bool esMaterialBrillante;

//Variables generales de luces
varying vec3 normal, lightDir, eyeVec;

const float cos_outer_cone_angle = 0.9; // 36 degrees


void main()
{
	//ESTE COLOR ES POR SI NO ENTRA A NINGUN IF
	gl_FragColor = vec4(1.0, 0.0, 0.0, 1.0);

	
	vec4 final_color = 
	(gl_FrontLightModelProduct.sceneColor * gl_FrontMaterial.ambient) + 
	(gl_LightSource[0].ambient * gl_FrontMaterial.ambient);
							
	vec3 L = normalize(lightDir);
	vec3 D = normalize(gl_LightSource[0].spotDirection);
	
	float cos_cur_angle = dot(-L, D);
	
	float cos_inner_cone_angle = gl_LightSource[0].spotCosCutoff;
	
	float cos_inner_minus_outer_angle = cos_inner_cone_angle - cos_outer_cone_angle;
	
	if (cos_cur_angle > cos_inner_cone_angle) 
	{
		vec3 N = normalize(normal);
	
		float lambertTerm = max( dot(N,L), 0.0);
		if(lambertTerm > 0.0)
		{
			final_color += gl_LightSource[0].diffuse * 
			gl_FrontMaterial.diffuse * 
			lambertTerm;	
		
			vec3 E = normalize(eyeVec);
			vec3 R = reflect(-L, N);
			
			float specular = pow( max(dot(R, E), 0.0), 
			gl_FrontMaterial.shininess );
			
			final_color += gl_LightSource[0].specular * 
			gl_FrontMaterial.specular * 
			specular;	
		}
	}
	else if( cos_cur_angle > cos_outer_cone_angle )
	{
		float falloff = (cos_cur_angle - cos_outer_cone_angle) / 
		cos_inner_minus_outer_angle;
		
		vec3 N = normalize(normal);
	
		float lambertTerm = max( dot(N,L), 0.0);
		if(lambertTerm > 0.0)
		{
			final_color += gl_LightSource[0].diffuse * 
			gl_FrontMaterial.diffuse * 
			lambertTerm * falloff;	
		
			vec3 E = normalize(eyeVec);
			vec3 R = reflect(-L, N);
			
			float specular = pow( max(dot(R, E), 0.0), 
			gl_FrontMaterial.shininess );
			
			final_color += gl_LightSource[0].specular * 
			gl_FrontMaterial.specular * 
			specular * falloff;	
		}
	}
	
	gl_FragColor = final_color;		
}*/