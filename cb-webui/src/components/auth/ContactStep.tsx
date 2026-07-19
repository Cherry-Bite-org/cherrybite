import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { z } from "zod";
import { useSendOtp } from "../../hooks/useSendOtp";
import { useAuthStore } from "../../store/authStore";
import { toast } from "sonner";
import { Mail, Phone, Loader2, User } from "lucide-react";
import { AxiosError } from "axios";

// Validation Schema
const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
const phoneRegex = /^\d{10}$/;

const contactSchema = z.object({
  identifier: z
    .string()
    .trim()
    .min(1, "Email or phone number is required")
    .refine(
      (val) => emailRegex.test(val) || phoneRegex.test(val),
      {
        message: "Enter a valid email address or a 10-digit phone number",
      }
    ),
});

type ContactFormValues = z.infer<typeof contactSchema>;

interface ContactStepProps {
  onSuccess: (identifier: string) => void;
  initialIdentifier?: string;
}

export default function ContactStep({ onSuccess, initialIdentifier = "" }: ContactStepProps) {
  const {
    register,
    handleSubmit,
    watch,
    formState: { errors },
  } = useForm<ContactFormValues>({
    resolver: zodResolver(contactSchema),
    defaultValues: {
      identifier: initialIdentifier,
    },
  });

  const { mutate: sendOtp, isPending } = useSendOtp();

  // Watch identifier for dynamic icon change
  const currentIdentifier = watch("identifier", "");
  const isDigits = /^[0-9]+$/.test(currentIdentifier);
  
  const getIcon = () => {
    if (!currentIdentifier) return <User className="w-[20px] h-[20px] transition-colors" />;
    if (isDigits) return <Phone className="w-[20px] h-[20px] transition-colors" />;
    return <Mail className="w-[20px] h-[20px] transition-colors" />;
  };

  const onSubmit = (data: ContactFormValues) => {
    sendOtp(
      { identifier: data.identifier },
      {
        onSuccess: (response) => {
          toast.success(response.message || "OTP sent successfully!");
          useAuthStore.getState().setIdentifier(data.identifier);
          onSuccess(data.identifier);
        },
        onError: (error) => {
          let errMsg = "Failed to send verification code. Please try again.";
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
      className="w-full max-w-[440px] mx-auto bg-theme-card border border-theme-border shadow-[0_8px_30px_rgba(0,0,0,0.02)] dark:shadow-[0_8px_30px_rgba(0,0,0,0.3)] hover:shadow-[0_12px_40px_rgba(0,0,0,0.04)] dark:hover:shadow-[0_12px_40px_rgba(0,0,0,0.4)] flex flex-col transition-all duration-300"
      style={{
        borderRadius: "32px",
        padding: "48px 40px",
        gap: "32px",
      }}
    >
      <div 
        className="flex flex-col items-center text-center"
        style={{ gap: "8px" }}
      >
        <h1 className="text-[26px] font-extrabold text-theme-text tracking-tight leading-tight transition-colors">
          Join the Club 🍷
        </h1>
        <p className="text-theme-subtext text-[14px] sm:text-[16px] font-medium transition-colors">
          Start discovering real food.
        </p>
      </div>

      <form 
        onSubmit={handleSubmit(onSubmit)} 
        className="flex flex-col" 
        style={{ gap: "24px" }}
        noValidate
      >
        <div className="flex flex-col" style={{ gap: "8px" }}>
          <div className="relative flex items-center">
            <div 
              className="absolute pointer-events-none text-theme-subtext flex items-center justify-center transition-colors"
              style={{ left: "16px" }}
            >
              {getIcon()}
            </div>
            <input
              id="identifier"
              type="text"
              placeholder="Phone number or email"
              disabled={isPending}
              style={{
                borderRadius: "16px",
                paddingTop: "16px",
                paddingBottom: "16px",
                paddingLeft: "48px",
                paddingRight: "16px",
              }}
              className={`w-full bg-theme-input-bg text-theme-text placeholder-theme-subtext/40 border text-[14px] transition-all focus:outline-none focus:ring-2 ${
                errors.identifier
                  ? "border-red-500/50 focus:border-red-500 focus:ring-red-500/20"
                  : "border-theme-input-border focus:border-theme-accent focus:ring-theme-accent/20"
              }`}
              {...register("identifier")}
            />
          </div>
          {errors.identifier && (
            <p className="text-[12px] font-medium text-red-500 mt-[4px] pl-[4px]">
              {errors.identifier.message}
            </p>
          )}
        </div>

        <button
          type="submit"
          disabled={isPending}
          style={{
            borderRadius: "16px",
            paddingTop: "16px",
            paddingBottom: "16px",
          }}
          className="w-full bg-theme-btn-bg hover:bg-theme-btn-hover text-white font-bold transition-all duration-300 shadow-md shadow-theme-btn-bg/10 hover:shadow-lg hover:shadow-theme-btn-hover/20 active:scale-[0.98] disabled:opacity-50 disabled:pointer-events-none flex items-center justify-center gap-[8px] cursor-pointer"
        >
          {isPending ? (
            <>
              <Loader2 className="w-[20px] h-[20px] animate-spin" />
              <span>Sending Code...</span>
            </>
          ) : (
            <span>Continue</span>
          )}
        </button>
      </form>

      {/* <div className="text-center">
        <p className="text-[14px] text-theme-subtext font-medium transition-colors">
          Already have an account?{" "}
          <a
            href="/login"
            onClick={(e) => {
              e.preventDefault();
            }}
            className="text-theme-btn-bg hover:text-theme-btn-hover font-semibold transition-colors duration-200"
          >
            Log in
          </a>
        </p>
      </div> */}
    </div>
  );
}
