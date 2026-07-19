export interface DecodedToken {
  userId: string;
  username: string;
  role: string;
  exp: number;
  iat: number;
}

export function decodeJwt(token: string): DecodedToken | null {
  try {
    const parts = token.split(".");
    if (parts.length !== 3) return null;

    const payload = parts[1];
    // Replace base64url characters to standard base64
    const base64 = payload.replace(/-/g, "+").replace(/_/g, "/");
    
    // Decode with atob and handle Unicode characters correctly
    const jsonPayload = decodeURIComponent(
      atob(base64)
        .split("")
        .map((c) => "%" + ("00" + c.charCodeAt(0).toString(16)).slice(-2))
        .join("")
    );

    const parsed = JSON.parse(jsonPayload);
    
    return {
      userId: parsed.userId || parsed.sub || "",
      username: parsed.username || parsed.userName || "",
      role: parsed.role || "",
      exp: parsed.exp || 0,
      iat: parsed.iat || 0,
    };
  } catch (error) {
    console.error("Failed to decode JWT:", error);
    return null;
  }
}
