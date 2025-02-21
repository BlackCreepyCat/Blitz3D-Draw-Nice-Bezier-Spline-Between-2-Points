; -----------------------------------------
; Name : Draw Bezier Curve Between 2 Points 
; Date : (C)2025 By Filax/CreepyCat
; Site : https://github.com/BlackCreepyCat
; -----------------------------------------

Function DrawSplineBezier(x1#, y1#, x2#, y2#, segments%, AutoCurve% = True , Invert% = False)
    Local i#, j#, t#, x#, y#, prevX#, prevY#
    Local cx1#, cy1#, cx2#, cy2#
	
    Local distance#

    ; Calcul de la distance entre les deux points
    distance = Sqr((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1))

    ; Calcul automatique des points de contrôle si AutoCurve est activé
    If AutoCurve
	
        ; Calcul des points de contrôle basés sur la distance
        CurveFactor% = Int(distance * 0.5)  ; Ajuste la courbure en fonction de la distance

		Select Invert%

		Case False
        	cx1 = x1 - CurveFactor%             ; Fraction de la distance pour le premier point de contrôle
        	cx2 = x2 + CurveFactor%            ; Symétrique pour le second point de contrôle		
		Case True
        	cx1 = x1 + CurveFactor%             ; Fraction de la distance pour le premier point de contrôle
        	cx2 = x2 - CurveFactor%            ; Symétrique pour le second point de contrôle
		End Select

		cy1 = y1  
        cy2 = y2
		
    Else
	
        ; Si AutoCurve est désactivé, utiliser les points de contrôle fournis par l'utilisateur
        cx1 = x1
        cy1 = y1
        cx2 = x2
        cy2 = y2
		
    End If

    prevX = x1
    prevY = y1
    
    For i = 1 To segments
        t = Float(i) / segments
        j = 1 - t
        x = j * j * j * x1 + 3 * j * j * t * cx1 + 3 * j * t * t * cx2 + t * t * t * x2
        y = j * j * j * y1 + 3 * j * j * t * cy1 + 3 * j * t * t * cy2 + t * t * t * y2
        Line prevX, prevY, x, y
        prevX = x
        prevY = y
    Next
End Function

Graphics 1024,768,0,2
SetBuffer BackBuffer()

; ---------------------------------------------------------------------------------------
; x1, y1 	= Coordonnées du premier point (départ de la courbe).
; cx1, cy1 	= Coordonnées du premier point de contrôle (influence la tangente au départ).
; cx2, cy2 	= Coordonnées du second point de contrôle (influence la tangente à l'arrivée).
; x2, y2 	= Coordonnées du second point (fin de la courbe).
; segments 	= Nombre de segments pour approximer la courbe (plus élevé = plus fluide).
; ---------------------------------------------------------------------------------------
Local NewX = 20
Local NewY = 30
Local Invert% = False

While Not KeyDown(1)
    Cls

	If MouseHit(2) Then
		NewX = MouseX()
		NewY = MouseY()
	EndIf

	If MouseHit(3) Then
		Invert% = 1 - Invert%
	EndIf	

	
    DrawSplineBezier(NewX, NewY,    MouseX(), MouseY(), 20 , True , Invert%)
    Flip
Wend
End
