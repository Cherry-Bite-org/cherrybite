import { useState, useEffect } from "react";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { z } from "zod";
import { useNavigate } from "react-router-dom";
import { useRegister } from "../../hooks/useRegister";
import { useCheckUsername } from "../../hooks/useCheckUsername";
import { useAuthStore } from "../../store/authStore";
import { decodeJwt } from "../../utils/jwt";
import { toast } from "sonner";
import { User, Loader2, CheckCircle2, XCircle, ArrowLeft } from "lucide-react";
import { AxiosError } from "axios";

// Validation Schema
const profileSchema = z.object({
  fullName: z
    .string()
    .trim()
    .min(1, "Full name is required")
    .min(3, "Full name must be at least 3 characters"),
  userName: z
    .string()
    .trim()
    .min(1, "Username is required")
    .min(4, "Username must be at least 4 characters")
    .max(20, "Username must be at most 20 characters")
    .regex(/^[a-z0-9_]+$/, "Username must contain only lowercase letters, numbers, and underscores"),
});

type ProfileFormValues = z.infer<typeof profileSchema>;

interface CompleteProfileFormProps {
  onBack: () => void;
}

export default function CompleteProfileForm({ onBack }: CompleteProfileFormProps) {
  const navigate = useNavigate();
  const identifier = useAuthStore((state) => state.identifier) || "";
  const temporaryToken = useAuthStore((state) => state.temporaryToken);
  const login = useAuthStore((state) => state.login);

  const {
    register,
    handleSubmit,
    watch,
    formState: { errors, isValid },
  } = useForm<ProfileFormValues>({
    resolver: zodResolver(profileSchema),
    mode: "onChange",
    defaultValues: {
      fullName: "",
      userName: "",
    },
  });

  const { mutate: registerUser, isPending: isRegistering } = useRegister();

  // Watch username for live check
  const username = watch("userName", "");
  const [debouncedUsername, setDebouncedUsername] = useState("");

  // Debounce username input by 500ms
  useEffect(() => {
    const timer = setTimeout(() => {
      setDebouncedUsername(username);
    }, 500);

    return () => clearTimeout(timer);
  }, [username]);

  // Check if username satisfies formatting before calling API
  const isUsernameFormatValid = username.length >= 4 && username.length <= 20 && /^[a-z0-9_]+$/.test(username);
  
  // API Call to check availability
  const { 
    data: isAvailable, 
    isLoading: isCheckingUsername,
  } = useCheckUsername(
    debouncedUsername,
    isUsernameFormatValid && debouncedUsername === username
  );

  const isTyping = username !== debouncedUsername;
  const showUsernameError = errors.userName;

  // Determine if username status indicator should show
  const showUsernameStatus = isUsernameFormatValid && !showUsernameError;

  // Disable button if form is invalid, checking username, or username is taken
  const isSubmitDisabled = 
    !isValid || 
    isCheckingUsername || 
    isTyping || 
    isAvailable === false || 
    isRegistering;

  const onSubmit = (data: ProfileFormValues) => {
    if (!temporaryToken) {
      toast.error("Session expired. Please restart the registration process.");
      onBack();
      return;
    }

    const isEmail = identifier.includes("@");
    
    registerUser(
      {
        temporaryToken,
        fullName: data.fullName,
        userName: data.userName,
        email: isEmail ? identifier : null,
        phoneNumber: isEmail ? null : identifier,
      },
      {
        onSuccess: (response) => {
          const decoded = decodeJwt(response.accessToken);
          if (decoded) {
            login(response.accessToken, response.refreshToken, decoded);
            toast.success(response.message || "Account created successfully!");
            navigate("/home");
          } else {
            toast.error("Failed to parse user details from token.");
          }
        },
        onError: (error) => {
          let errMsg = "Failed to create account. Please try again.";
          if (error instanceof AxiosError && error.response?.data?.message) {
            errMsg = error.response.data.message;
          } else if (error.message) {
            errMsg = error.message;
          }
          toast.error(errMsg);
        },
      }
    );
  };

  return (
    <div 
      className="w-full max-w-[440px] mx-auto bg-theme-card border border-theme-border shadow-[0_8px_30px_rgba(0,0,0,0.02)] dark:shadow-[0_8px_30px_rgba(0,0,0,0.3)] hover:shadow-[0_12px_40px_rgba(0,0,0,0.04)] dark:hover:shadow-[0_12px_40px_rgba(0,0,0,0.4)] flex flex-col transition-all duration-300 relative"
      style={{
        borderRadius: "32px",
        padding: "48px 40px",
        gap: "32px",
      }}
    >
      {/* Back Button */}
      <button
        onClick={onBack}
        disabled={isRegistering}
        className="absolute p-[6px] text-theme-subtext hover:text-theme-accent hover:bg-theme-input-bg transition-all duration-200 cursor-pointer disabled:opacity-50"
        style={{
          top: "32px",
          left: "32px",
          borderRadius: "12px",
        }}
        aria-label="Back to OTP verification"
      >
        <ArrowLeft className="w-[20px] h-[20px]" />
      </button>

      <div 
        className="flex flex-col items-center text-center mt-[16px]"
        style={{ gap: "8px" }}
      >
        <h1 className="text-[26px] font-extrabold text-theme-text tracking-tight leading-tight transition-colors">
          Join the Club 🍷
        </h1>
        <p className="text-theme-subtext text-[14px] sm:text-[16px] font-medium transition-colors">
          Tell us a bit about yourself.
        </p>
      </div>

      <form 
        onSubmit={handleSubmit(onSubmit)} 
        className="flex flex-col" 
        style={{ gap: "24px" }}
        noValidate
      >
        {/* Full Name Field */}
        <div className="flex flex-col" style={{ gap: "8px" }}>
          <div className="relative flex items-center">
            <div 
              className="absolute pointer-events-none text-theme-subtext flex items-center justify-center transition-colors"
              style={{ left: "16px" }}
            >
              <User className="w-[20px] h-[20px]" />
            </div>
            <input
              id="fullName"
              type="text"
              placeholder="Full Name"
              disabled={isRegistering}
              style={{
                borderRadius: "16px",
                paddingTop: "16px",
                paddingBottom: "16px",
                paddingLeft: "48px",
                paddingRight: "16px",
              }}
              className={`w-full bg-theme-input-bg text-theme-text placeholder-theme-subtext/40 border text-[14px] transition-all focus:outline-none focus:ring-2 ${
                errors.fullName
                  ? "border-red-500/50 focus:border-red-500 focus:ring-red-500/20"
                  : "border-theme-input-border focus:border-theme-accent focus:ring-theme-accent/20"
              }`}
              {...register("fullName")}
            />
          </div>
          {errors.fullName && (
            <p className="text-[12px] font-medium text-red-500 mt-[4px] pl-[4px]">
              {errors.fullName.message}
            </p>
          )}
        </div>

        {/* Username Field */}
        <div className="flex flex-col" style={{ gap: "8px" }}>
          <div className="relative flex items-center">
            <div 
              className="absolute pointer-events-none text-theme-subtext font-bold text-[18px] flex items-center justify-center transition-colors"
              style={{ left: "18px" }}
            >
              @
            </div>
            <input
              id="userName"
              type="text"
              placeholder="Choose a username"
              disabled={isRegistering}
              style={{
                borderRadius: "16px",
                paddingTop: "16px",
                paddingBottom: "16px",
                paddingLeft: "48px",
                paddingRight: "16px",
              }}
              className={`w-full bg-theme-input-bg text-theme-text placeholder-theme-subtext/40 border text-[14px] transition-all focus:outline-none focus:ring-2 ${
                errors.userName
                  ? "border-red-500/50 focus:border-red-500 focus:ring-red-500/20"
                  : "border-theme-input-border focus:border-theme-accent focus:ring-theme-accent/20"
              }`}
              {...register("userName")}
            />
          </div>

          {/* Validation Errors or Live Availability Status */}
          {errors.userName ? (
            <p className="text-[12px] font-medium text-red-500 mt-[4px] pl-[4px]">
              {errors.userName.message}
            </p>
          ) : showUsernameStatus ? (
            <div className="flex items-center gap-[6px] mt-[4px] pl-[4px] text-[12px] transition-colors">
              {isCheckingUsername || isTyping ? (
                <>
                  <Loader2 className="w-[14px] h-[14px] animate-spin text-theme-subtext" />
                  <span className="text-theme-subtext font-medium">Checking availability...</span>
                </>
              ) : isAvailable ? (
                <>
                  <CheckCircle2 className="w-[14px] h-[14px] text-green-500 dark:text-green-400" />
                  <span className="text-green-600 dark:text-green-400 font-semibold">Username Available</span>
                </>
              ) : (
                <>
                  <XCircle className="w-[14px] h-[14px] text-red-500 dark:text-red-400" />
                  <span className="text-red-500 dark:text-red-400 font-semibold">Username already taken</span>
                </>
              )}
            </div>
          ) : null}
        </div>

        {/* Create Account Button */}
        <button
          type="submit"
          disabled={isSubmitDisabled}
          style={{
            borderRadius: "16px",
            paddingTop: "16px",
            paddingBottom: "16px",
          }}
          className="w-full bg-theme-btn-bg hover:bg-theme-btn-hover text-white font-bold transition-all duration-300 shadow-md shadow-theme-btn-bg/10 hover:shadow-lg hover:shadow-theme-btn-hover/20 active:scale-[0.98] disabled:opacity-50 disabled:pointer-events-none flex items-center justify-center gap-[8px] cursor-pointer"
        >
          {isRegistering ? (
            <>
              <Loader2 className="w-[20px] h-[20px] animate-spin" />
              <span>Creating Account...</span>
            </>
          ) : (
            <span>Create Account</span>
          )}
        </button>
      </form>
    </div>
  );
}
