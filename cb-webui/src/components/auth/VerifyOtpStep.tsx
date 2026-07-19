import { useState, useRef, useEffect } from "react";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { z } from "zod";
import { useVerifyOtp } from "../../hooks/useVerifyOtp";
import { useSendOtp } from "../../hooks/useSendOtp";
import { useAuthStore } from "../../store/authStore";
import { toast } from "sonner";
import { ArrowLeft, Loader2, RefreshCw } from "lucide-react";
import { AxiosError } from "axios";

// Validation Schema for OTP
const otpSchema = z.object({
  otp: z
    .string()
    .length(6, "Verification code must be exactly 6 digits")
    .regex(/^\d+$/, "Verification code must contain only numbers"),
});

type OtpFormValues = z.infer<typeof otpSchema>;

interface VerifyOtpStepProps {
  identifier: string;
  onBack: () => void;
  onVerified: (newUser: boolean) => void;
}

export default function VerifyOtpStep({ identifier, onBack, onVerified }: VerifyOtpStepProps) {
  const [otp, setOtp] = useState<string[]>(Array(6).fill(""));
  const [countdown, setCountdown] = useState(30);
  const inputRefs = useRef<(HTMLInputElement | null)[]>([]);

  const {
    register,
    handleSubmit,
    setValue,
    trigger,
    formState: { errors },
  } = useForm<OtpFormValues>({
    resolver: zodResolver(otpSchema),
    defaultValues: {
      otp: "",
    },
  });

  const { mutate: verifyOtp, isPending: isVerifying } = useVerifyOtp();
  const { mutate: sendOtp, isPending: isResending } = useSendOtp();

  // Focus the first input box on mount
  useEffect(() => {
    if (inputRefs.current[0]) {
      inputRefs.current[0].focus();
    }
  }, []);

  // Countdown timer logic
  useEffect(() => {
    if (countdown <= 0) return;

    const timer = setInterval(() => {
      setCountdown((prev) => prev - 1);
    }, 1000);

    return () => clearInterval(timer);
  }, [countdown]);

  const maskIdentifier = (val: string) => {
    if (!val) return "";
    if (val.includes("@")) {
      const [local, domain] = val.split("@");
      if (local.length <= 2) {
        return `${local[0] || ""}***@${domain}`;
      }
      return `${local.slice(0, 2)}***${local.slice(-1)}@${domain}`;
    }
    if (val.length >= 10) {
      return `******${val.slice(-4)}`;
    }
    return val;
  };

  const handleOtpChange = (value: string, index: number) => {
    // Allow only numerical input
    if (!/^\d*$/.test(value)) return;

    const newOtp = [...otp];
    newOtp[index] = value.slice(-1);
    setOtp(newOtp);

    const combinedOtp = newOtp.join("");
    setValue("otp", combinedOtp, { shouldValidate: true });

    // Focus next input box if typed a value
    if (value && index < 5) {
      inputRefs.current[index + 1]?.focus();
    }
  };

  const handleKeyDown = (e: React.KeyboardEvent<HTMLInputElement>, index: number) => {
    if (e.key === "Backspace") {
      if (!otp[index] && index > 0) {
        // Move to previous and clear it
        const newOtp = [...otp];
        newOtp[index - 1] = "";
        setOtp(newOtp);
        setValue("otp", newOtp.join(""), { shouldValidate: true });
        inputRefs.current[index - 1]?.focus();
      } else {
        // Clear current
        const newOtp = [...otp];
        newOtp[index] = "";
        setOtp(newOtp);
        setValue("otp", newOtp.join(""), { shouldValidate: true });
      }
    } else if (e.key === "ArrowLeft" && index > 0) {
      inputRefs.current[index - 1]?.focus();
    } else if (e.key === "ArrowRight" && index < 5) {
      inputRefs.current[index + 1]?.focus();
    }
  };

  const handlePaste = (e: React.ClipboardEvent<HTMLInputElement>) => {
    e.preventDefault();
    const pastedData = e.clipboardData.getData("text").trim();
    if (!/^\d{6}$/.test(pastedData)) {
      toast.error("Please paste a 6-digit numerical code");
      return;
    }

    const digits = pastedData.split("");
    setOtp(digits);
    setValue("otp", pastedData, { shouldValidate: true });
    
    // Autofill values in each input field
    digits.forEach((digit, i) => {
      if (inputRefs.current[i]) {
        inputRefs.current[i]!.value = digit;
      }
    });

    // Focus on the last input field after pasting
    inputRefs.current[5]?.focus();
    trigger("otp");
  };

  const handleResend = () => {
    if (countdown > 0 || isResending) return;

    sendOtp(
      { identifier },
      {
        onSuccess: (response) => {
          toast.success(response.message || "OTP resent successfully!");
          setCountdown(30); // reset countdown
          // Clear current OTP entries
          setOtp(Array(6).fill(""));
          setValue("otp", "");
          if (inputRefs.current[0]) {
            inputRefs.current[0].focus();
          }
        },
        onError: (error) => {
          let errMsg = "Failed to resend verification code. Please try again.";
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

  const onSubmit = (data: OtpFormValues) => {
    verifyOtp(
      { identifier, otp: data.otp },
      {
        onSuccess: (response) => {
          toast.success(response.message || "Verification successful!");
          onVerified(response.newUser);
        },
        onError: (error) => {
          let errMsg = "Verification failed. Please check the code and try again.";
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
        disabled={isVerifying || isResending}
        className="absolute p-[6px] text-theme-subtext hover:text-theme-accent hover:bg-theme-input-bg transition-all duration-200 cursor-pointer disabled:opacity-50"
        style={{
          top: "32px",
          left: "32px",
          borderRadius: "12px",
        }}
        aria-label="Back to contact input"
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
        <p className="text-theme-subtext text-[12px] font-semibold tracking-wide uppercase transition-colors">
          Verify your phone/email
        </p>
        <p className="text-[12px] text-theme-subtext font-medium leading-relaxed max-w-[280px] transition-colors">
          We sent a verification code to{" "}
          <span className="font-semibold text-theme-text transition-colors">{maskIdentifier(identifier)}</span>
        </p>
      </div>

      <form 
        onSubmit={handleSubmit(onSubmit)} 
        className="flex flex-col" 
        style={{ gap: "24px" }}
        noValidate
      >
        {/* Hidden field for react-hook-form controller */}
        <input type="hidden" {...register("otp")} />

        <div className="flex flex-col" style={{ gap: "8px" }}>
          <div className="flex justify-between" style={{ gap: "10px" }} onPaste={handlePaste}>
            {otp.map((digit, index) => (
              <input
                key={index}
                type="text"
                maxLength={1}
                value={digit}
                disabled={isVerifying}
                ref={(el) => {
                  inputRefs.current[index] = el;
                }}
                onChange={(e) => handleOtpChange(e.target.value, index)}
                onKeyDown={(e) => handleKeyDown(e, index)}
                style={{
                  width: "52px",
                  height: "60px",
                  borderRadius: "16px",
                  textAlign: "center",
                }}
                className={`text-xl font-bold bg-theme-input-bg text-theme-text border transition-all focus:outline-none focus:ring-2 ${
                  errors.otp
                    ? "border-red-500/50 focus:border-red-500 focus:ring-red-500/20"
                    : "border-theme-input-border focus:border-theme-accent focus:ring-theme-accent/20"
                }`}
              />
            ))}
          </div>

          {errors.otp && (
            <p className="text-[12px] font-medium text-red-500 text-center mt-[4px]">
              {errors.otp.message}
            </p>
          )}
        </div>

        <button
          type="submit"
          disabled={isVerifying || otp.some((d) => !d)}
          style={{
            borderRadius: "16px",
            paddingTop: "16px",
            paddingBottom: "16px",
          }}
          className="w-full bg-theme-btn-bg hover:bg-theme-btn-hover text-white font-bold transition-all duration-300 shadow-md shadow-theme-btn-bg/10 hover:shadow-lg hover:shadow-theme-btn-hover/20 active:scale-[0.98] disabled:opacity-50 disabled:pointer-events-none flex items-center justify-center gap-[8px] cursor-pointer"
        >
          {isVerifying ? (
            <>
              <Loader2 className="w-[20px] h-[20px] animate-spin" />
              <span>Verifying...</span>
            </>
          ) : (
            <span>Verify</span>
          )}
        </button>
      </form>

      <div 
        className="flex flex-col items-center"
        style={{ gap: "6px" }}
      >
        <p className="text-[12px] text-theme-subtext font-medium transition-colors">
          Didn't receive the code?
        </p>
        <button
          onClick={handleResend}
          disabled={countdown > 0 || isResending || isVerifying}
          className={`flex items-center gap-[6px] text-[12px] font-semibold transition-colors duration-200 ${
            countdown > 0 || isResending || isVerifying
              ? "text-theme-subtext/40 cursor-not-allowed"
              : "text-theme-btn-bg hover:text-theme-btn-hover cursor-pointer"
          }`}
        >
          {isResending ? (
            <>
              <RefreshCw className="w-[14px] h-[14px] animate-spin" />
              <span>Resending...</span>
            </>
          ) : countdown > 0 ? (
            <span>Resend code in {countdown}s</span>
          ) : (
            <>
              <RefreshCw className="w-[14px] h-[14px]" />
              <span>Resend OTP</span>
            </>
          )}
        </button>
      </div>
    </div>
  );
}
